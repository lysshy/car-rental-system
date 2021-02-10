package com.also.car.rental.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResultUtils {

    public static void toJSON(HttpServletResponse response, Object result, HttpStatus httpStatus) throws IOException {
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
