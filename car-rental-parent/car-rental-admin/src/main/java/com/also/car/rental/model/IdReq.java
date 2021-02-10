package com.also.car.rental.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class IdReq {

    @NotNull(message = "id must not be null")
    private Integer id;
}
