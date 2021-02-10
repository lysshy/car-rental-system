package com.also.car.rental.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CarRentalRecordView {

    private Integer id;

    private LocalDateTime rentTime;

    private LocalDateTime returnTime;

    private Integer brandType;

    private String brandName;

    private String plateNumber;

    private String userName;

    private String mobile;

    private Integer status;

    private LocalDateTime createTime;
}
