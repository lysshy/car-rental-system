package com.also.car.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ApiModel("login request object")
public class LoginReq {

    @NotEmpty(message = "username can not be empty")
    @ApiModelProperty("username")
    private String username;

    @NotEmpty(message = "password can not be empty")
    @ApiModelProperty("password")
    private String password;

    private boolean rememberMe = false;
}
