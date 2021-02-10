package com.also.car.rental.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtUserException extends RuntimeException {

    private String msgKey;

    public JwtUserException(String msgKey) {
        super(msgKey);
        this.msgKey = msgKey;
    }
}
