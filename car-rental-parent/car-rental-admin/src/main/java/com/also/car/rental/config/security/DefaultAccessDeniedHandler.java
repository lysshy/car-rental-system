package com.also.car.rental.config.security;


import com.also.car.rental.emuns.ResultCode;
import com.also.car.rental.entity.base.BaseResult;
import com.also.car.rental.utils.ResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException {
        //登陆状态下，权限不足执行该方法
        BaseResult baseResult = BaseResult.fail(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
        ResultUtils.toJSON(response, baseResult, HttpStatus.FORBIDDEN);
    }
}