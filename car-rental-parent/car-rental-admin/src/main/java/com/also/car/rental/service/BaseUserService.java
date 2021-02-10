package com.also.car.rental.service;


import com.also.car.rental.entity.BaseUser;

public interface BaseUserService {

    BaseUser selectById(long id);

    void insertUser(BaseUser user);

    BaseUser findByUsername(String username);
}
