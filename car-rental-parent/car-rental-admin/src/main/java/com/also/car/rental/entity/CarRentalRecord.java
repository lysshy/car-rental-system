package com.also.car.rental.entity;

import com.also.car.rental.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@TableName("sys_car_rental_record")
public class CarRentalRecord extends BaseEntity {

    private LocalDateTime rentTime;

    private LocalDateTime returnTime;

    private String userName;

    private Integer carId;

    private String mobile;

    private Integer status;

    private int totalPrice;

    private LocalDateTime actualReturnTime;

}
