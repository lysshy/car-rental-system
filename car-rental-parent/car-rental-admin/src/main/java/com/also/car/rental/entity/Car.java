package com.also.car.rental.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("sys_car")
public class Car {

    private Integer id;

    private String plateNumber;

    private Integer brandType;

    private Integer status;

    private LocalDateTime updateTime;

    private int rentPrice;
}
