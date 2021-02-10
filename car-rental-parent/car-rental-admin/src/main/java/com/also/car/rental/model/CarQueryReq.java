package com.also.car.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@ApiModel("car reserve query request object")
public class CarQueryReq {

    @ApiModelProperty("car brand type")
    private Integer brandType;

    @NotNull(message = "reserve start time can not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "car reserve start time", example = "2021-02-01 00:00:00")
    private LocalDateTime rentTime;

    @NotNull(message = "reserve end time can not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "car reserve end time", example = "2021-02-01 00:00:00")
    private LocalDateTime returnTime;
}
