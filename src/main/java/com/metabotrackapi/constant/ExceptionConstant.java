package com.metabotrackapi.constant;

import lombok.Data;

@Data
public class ExceptionConstant {
    public static final String NOT_FOUND = "The requested API endpoint was not found.";
    public static final String INTERNAL_SERVER_ERROR = "An unexpected internal server error occurred. Please contact support.";
}
