package com.also.car.rental.utils;



import com.also.car.rental.config.security.JwtConfig;
import com.also.car.rental.config.security.UserDetail;
import com.also.car.rental.config.security.cache.UserLoginCacheService;
import com.also.car.rental.exception.JwtUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;


@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
@Component
public class JwtUtil {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserLoginCacheService userLoginCacheService;

    /**
     * 创建JWT
     *
     * @param rememberMe  记住我
     * @param id          用户id
     * @param subject     用户名
     * @param authorities 用户权限
     * @return JWT
     */
    public String createJWT(Boolean rememberMe, Long id, String subject, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .claim("authorities", authorities);

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0) {
            builder.setExpiration(DateUtils.plusMillisecond(now, ttl.intValue()));
        }
        String jwt = builder.compact();
        userLoginCacheService.put(subject, jwt);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe) {
        UserDetail userPrincipal = (UserDetail) authentication.getPrincipal();
        return createJWT(rememberMe, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getAuthorities());
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();

            // 校验redis中的JWT是否存在
            String cacheJwt = userLoginCacheService.get(username);
            if (StringUtils.isEmpty(cacheJwt)) {
                throw new JwtUserException("jwt.user.token.illegal");
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            if (!StringUtils.equals(jwt, cacheJwt)) {
                throw new JwtUserException("jwt.user.token.expire");
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new JwtUserException("jwt.user.token.expire");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new JwtUserException("jwt.user.token.unsupported");
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new JwtUserException("jwt.user.token.invalid");
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new JwtUserException("jwt.user.token.invalid");
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new JwtUserException("jwt.user.token.illegal");
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT

    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

}
