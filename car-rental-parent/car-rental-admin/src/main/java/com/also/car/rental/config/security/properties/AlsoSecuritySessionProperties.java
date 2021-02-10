package com.also.car.rental.config.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "also.security.session", ignoreInvalidFields = true)
@Component
@Setter
@Getter
public class AlsoSecuritySessionProperties {

    private String loginRequestUrl = "/login";

    private String loginRequestType = "post";

    private String loginSuccessUrl = "/loginSuccess";

    private ValidateCodeProperties validateCode = new ValidateCodeProperties();
}
