package com.also.car.rental.emuns;

import lombok.Getter;

@Getter
public enum ResultCode {
    /*
    请求返回状态码和说明信息
     */
    SUCCESS("0000", "success"),


    BAD_REQUEST("400", "参数或者语法不对"),
    UNAUTHORIZED("401", "认证失败"),
    LOGIN_ERROR("401", "登陆失败，用户名或密码无效"),
    FORBIDDEN("403", "禁止访问"),
    NOT_FOUND("404", "请求的资源不存在"),
    OPERATE_ERROR("405", "操作失败，请求操作的资源不存在"),
    TIME_OUT("408", "请求超时"),

    SERVER_ERROR("500", "Inner server error"),


    CAR_STOCK_NOT_EXIST("2000", "库存记录不存在"),
    CAR_STOCK_ZERO("2001", "The car of this brand is out of stock"),
    CAR_ALREADY_SELECT("2002", "车已经被其他用户锁定，请稍后再试"),
    CAR_ALREADY_RETURN("2003", "Car has already return"),
    CAR_NOT_EXIST("2004", "Car is not exist"),
    DATETIME_INVALID("2005", "Start time is after end time"),

    RECORD_NOT_EXIST("3000", "Car rental record is not exist"),
    CAR_ALREADY_TAKE("3001", "Car has already been taken"),
    TAKE_CAR_EARLY("3002", "Can not take car before reserve start time"),
;
    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
