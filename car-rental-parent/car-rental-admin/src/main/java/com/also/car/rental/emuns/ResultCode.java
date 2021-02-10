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

    SERVER_ERROR("500", "服务器内部错误"),


    CAR_STOCK_NOT_EXIST("2000", "库存记录不存在"),
    CAR_STOCK_ZERO("2001", "该品牌车型没有库存"),
    CAR_ALREADY_SELECT("2002", "车已经被其他用户锁定，请稍后再试"),
    CAR_ALREADY_RETURN("2003", "该车已归还"),
    CAR_NOT_EXIST("2004", "car is not exist"),

    RECORD_NOT_EXIST("3000", "car rental record is not exist"),
;
    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
