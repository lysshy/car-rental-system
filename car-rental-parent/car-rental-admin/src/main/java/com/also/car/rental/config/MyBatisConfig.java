package com.also.car.rental.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.also.**.mapper")
public class MyBatisConfig {
}
