package com.also.car.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("car reserve record query object")
public class CarRentalRecordQueryReq {

    @ApiModelProperty("car brand type")
    private Integer brandType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "car reserve start time", example = "2021-02-01 00:00:00")
    private LocalDateTime rentTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "car reserve end time", example = "2021-02-01 00:00:00")
    private LocalDateTime returnTime;
}
