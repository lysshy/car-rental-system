package com.also.car.rental.emuns;

import lombok.Getter;

@Getter
public enum BrandEnum {
    TOYOTA(1, "Toyota Camry"),
    BMW(2, "BMW 650");

    private final int code;
    private final String name;

    BrandEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getByCode(int code) {
        for (BrandEnum brandEnum : values()) {
            if (brandEnum.getCode() == code) {
                return brandEnum.getName();
            }
        }
        return null;
    }
}
