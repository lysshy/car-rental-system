package com.also.car.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class CarRentalReq {

    @NotNull(message = "brand type can not be empty")
    private Integer brandType;

    @NotEmpty(message = "mobile can not be empty")
    private String mobile;

    private String userName;

    @NotNull(message = "reserve start time can not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rentTime;

    @NotNull(message = "reserve end time can not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime returnTime;
}
