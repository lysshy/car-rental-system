package com.also.car.rental.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class LoginByMobileReq {

    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    private boolean rememberMe = false;
}
