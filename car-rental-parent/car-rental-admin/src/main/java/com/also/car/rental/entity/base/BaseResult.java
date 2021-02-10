package com.also.car.rental.entity.base;

import com.also.car.rental.emuns.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseResult<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public BaseResult(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> BaseResult<T> ok(T data) {
        BaseResult<T> baseResult = build(ResultCode.SUCCESS);
        baseResult.setData(data);
        return baseResult;
    }

    public static <T> BaseResult<T> ok() {
        return build(ResultCode.SUCCESS);
    }

    public static  <T> BaseResult<T> fail(String code, String msg) {
        return new BaseResult<>(code, msg);
    }

    public static <T> BaseResult<T> build(ResultCode resultCode) {
        return new BaseResult<>(resultCode.getCode(), resultCode.getMsg());
    }
}
