package com.also.car.rental.exception;


import com.also.car.rental.emuns.ResultCode;
import com.also.car.rental.entity.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 处理所有自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public BaseResult handleCustomException(CustomException e){
        log.error(e.getMsg());
        return BaseResult.fail(e.getCode(), e.getMsg());
    }
    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        return BaseResult.fail(ResultCode.BAD_REQUEST.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResult handleBadCredentialsException(BadCredentialsException e){
        log.error("BadCredentialsException", e);
        return BaseResult.fail(ResultCode.UNAUTHORIZED.getCode(), "username or password invalid");
    }

    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e){
        log.error("Exception", e);
        return BaseResult.fail(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMsg());
    }
}
