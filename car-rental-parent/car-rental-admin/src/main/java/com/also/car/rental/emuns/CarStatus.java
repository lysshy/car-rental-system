package com.also.car.rental.emuns;

import lombok.Getter;

@Getter
public enum CarStatus {
    IN_STOCK(0),
    OUT_STOCK(1);

    private final int code;

    CarStatus(int code) {
        this.code = code;
    }
}
