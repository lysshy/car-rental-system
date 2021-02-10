package com.also.car.rental.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class LoginReq {

    @NotEmpty(message = "username can not be empty")
    private String username;

    @NotEmpty(message = "password can not be empty")
    private String password;

    private boolean rememberMe = false;
}
