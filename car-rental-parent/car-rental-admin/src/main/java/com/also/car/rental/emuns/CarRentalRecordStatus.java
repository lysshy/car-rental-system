package com.also.car.rental.emuns;

import lombok.Getter;

@Getter
public enum CarRentalRecordStatus {
    RESERVE(0, "reserve"),
    USING_CAR(1, "using car"),
    FINISHED(2, "finished");

    private final int code;
    private final String name;

    CarRentalRecordStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getByCode(int code) {
        for (CarRentalRecordStatus e : values()) {
            if (e.getCode() == code) {
                return e.getName();
            }
        }
        return null;
    }
}
