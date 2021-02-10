package com.also.car.rental.config.security.properties;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
public class IgnoreUrlProperties {

    public static final String TYPE_ALL = "ALL";

    private List<String> urls = new ArrayList<>();

    public Map<String, Set<String>> toUnmodifiableMap() {
        Map<String, Set<String>> map = new HashMap<>();

        for (String url : urls) {
            String[] temps = url.split(":");
            if (temps.length > 1) {
                //带有请求方式
                map.computeIfAbsent(temps[0].toUpperCase(), k -> new HashSet<>()).add(temps[1]);
            } else {
                map.computeIfAbsent(TYPE_ALL, k -> new HashSet<>()).add(temps[0]);
            }
        }
        return Collections.unmodifiableMap(map);
    }
}
