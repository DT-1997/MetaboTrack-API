package com.metabotrackapi.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Default constructor for standard bad requests
     */
    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }
}

