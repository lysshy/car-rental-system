package com.also.car.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("login response")
public class LoginView {

    @ApiModelProperty("username")
    private String username;

    @ApiModelProperty("token")
    private String token;
}
