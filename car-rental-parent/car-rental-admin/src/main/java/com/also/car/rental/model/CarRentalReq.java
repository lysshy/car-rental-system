package com.also.car.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter

public class CarRentalReq {

    @NotNull(message = "brand type can not be empty")
    @ApiModelProperty("car brand type")
    private Integer brandType;

    @NotEmpty(message = "mobile can not be empty")
    @ApiModelProperty("customer mobile number")
    private String mobile;

    @ApiModelProperty("customer username")
    @NotEmpty(message = "username can not be empty")
    private String userName;

    @NotNull(message = "reserve start time can not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "car reserve start time", example = "2021-02-01 00:00:00")
    private LocalDateTime rentTime;

    @NotNull(message = "reserve end time can not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "car reserve end time", example = "2021-02-01 00:00:00")
    private LocalDateTime returnTime;
}
