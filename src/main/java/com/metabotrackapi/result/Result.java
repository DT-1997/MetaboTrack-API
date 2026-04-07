package com.metabotrackapi.result;


import lombok.Data;

import java.io.Serializable;


/**
 * Global Standard API Response Wrapper
 * @param <T>
 */

@Data
public class Result<T> implements Serializable {

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer DEFAULT_ERROR_CODE = 500;

    private Integer code;

    private String msg;

    private T data;

    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Success - No Data returned
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = SUCCESS_CODE;
        result.msg = "Success";
        return result;
    }

    /**
     * Success - Data returned
     */
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = SUCCESS_CODE;
        result.msg = "Success";
        return result;
    }

    /**
     * ERROR - Default Server Error
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.data = null;
        result.code = DEFAULT_ERROR_CODE;
        result.msg = msg;
        return result;
    }

    /**
     * ERROR - Custom Error Code
     */
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.code = code;
        result.msg = msg;
        return result;
    }


}
