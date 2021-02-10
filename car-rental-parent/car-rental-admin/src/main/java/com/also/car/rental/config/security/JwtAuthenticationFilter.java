package com.also.car.rental.config.security;

import com.also.car.rental.config.security.properties.IgnoreUrlProperties;
import com.also.car.rental.emuns.ResultCode;
import com.also.car.rental.entity.base.BaseResult;
import com.also.car.rental.exception.JwtUserException;
import com.also.car.rental.utils.JwtUtil;
import com.also.car.rental.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IgnoreUrlProperties ignoreUrlProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.getJwtFromRequest(request);

        if (StringUtils.isNotBlank(jwt)) {
            try {
                String username = jwtUtil.getUsernameFromJWT(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (JwtUserException e) {
                logger.error(e);
                String msg = e.getMsgKey();
                BaseResult baseResult = BaseResult.fail(ResultCode.UNAUTHORIZED.getCode(), msg);
                ResultUtils.toJSON(response, baseResult, HttpStatus.UNAUTHORIZED);
            }
        } else {
            BaseResult baseResult = BaseResult.build(ResultCode.UNAUTHORIZED);
            ResultUtils.toJSON(response, baseResult, HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (Objects.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }
        Map<String, Set<String>> ignoreMap = ignoreUrlProperties.toUnmodifiableMap();
        if (!CollectionUtils.isEmpty(ignoreMap)) {
            Set<String> ignores = ignoreMap.get(httpMethod.name());
            if (!CollectionUtils.isEmpty(ignores)) {
                for (String ignore : ignores) {
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                    if (matcher.matches(request)) {
                        return true;
                    }
                }
            }
            ignores = ignoreMap.get(IgnoreUrlProperties.TYPE_ALL);
            if (!CollectionUtils.isEmpty(ignores)) {
                for (String ignore : ignores) {
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore);
                    if (matcher.matches(request)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
