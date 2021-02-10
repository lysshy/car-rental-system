package com.also.car.rental.exception;


import com.also.car.rental.emuns.ResultCode;
import com.also.car.rental.entity.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
}
