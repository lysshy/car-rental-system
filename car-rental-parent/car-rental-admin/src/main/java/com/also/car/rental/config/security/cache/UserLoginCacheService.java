package com.also.car.rental.config.security.cache;

public interface UserLoginCacheService {

    String get(String username);

    void put(String username, String jwt);
}
