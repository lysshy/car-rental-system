package com.also.car.rental.config.security.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultLoginCacheServiceImpl implements UserLoginCacheService {

    private static final Map<String, String> loginMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public String get(String username) {
        return loginMap.get(username);
    }

    @Override
    public void put(String username, String jwt) {
        loginMap.put(username, jwt);
    }
}
