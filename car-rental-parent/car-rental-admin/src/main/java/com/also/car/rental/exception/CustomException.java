package com.also.car.rental.exception;

import com.also.car.rental.emuns.ResultCode;
import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {

    private String code;

    private String msg;

    public CustomException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
}
