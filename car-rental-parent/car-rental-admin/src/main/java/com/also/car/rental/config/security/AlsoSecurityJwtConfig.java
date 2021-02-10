package com.also.car.rental.config.security;


import com.also.car.rental.config.security.cache.DefaultLoginCacheServiceImpl;
import com.also.car.rental.config.security.cache.UserLoginCacheService;
import com.also.car.rental.config.security.properties.IgnoreUrlProperties;
import com.also.car.rental.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class AlsoSecurityJwtConfig {

    private final MessageSource messageSource;

    public AlsoSecurityJwtConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        if (messageSource instanceof ResourceBundleMessageSource) {
            ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) messageSource;
            resourceBundleMessageSource.addBasenames("messages/messages-also-security-jwt");
        }
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtUtil jwtUtil) {
        return new JwtAuthenticationSuccessHandler(jwtUtil);
    }

    @Bean
    @ConditionalOnProperty(name = "also.cache.type", matchIfMissing = true)
    @ConditionalOnMissingBean
    public UserLoginCacheService userLoginCacheService() {
        log.info("use DefaultLoginCacheServiceImpl");
        return new DefaultLoginCacheServiceImpl();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    @Bean
    @ConfigurationProperties(prefix = "also.security.ignore")
    public IgnoreUrlProperties ignoreUrlProperties() {
        return new IgnoreUrlProperties();
    }

}
