package com.also.car.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("car rental view")
public class CarRentalView {

    @ApiModelProperty("car brand type")
    private int brandType;

    @ApiModelProperty("car brand name")
    private String brandName;

    @ApiModelProperty("car rental price")
    private int rentPrice;

    @ApiModelProperty("stock of car")
    private int stock;

}
