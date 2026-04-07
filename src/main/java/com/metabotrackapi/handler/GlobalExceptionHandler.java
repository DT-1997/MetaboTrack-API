package com.metabotrackapi.handler;

import com.metabotrackapi.constant.ExceptionConstant;
import com.metabotrackapi.exception.BusinessException;
import com.metabotrackapi.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle Custom Business Exceptions
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleBusinessException(BusinessException e) {
        log.warn("Business Exception caught: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * Handle Parameter Validation Exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder("Validation failed: ");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsg.append(fieldError.getField()).append(" ")
                    .append(fieldError.getDefaultMessage()).append("; ");
        }

        log.warn("Validation Exception: {}", errorMsg.toString());
        return Result.error(HttpStatus.BAD_REQUEST.value(), errorMsg.toString());
    }

    /**
     * Handle 404 Not Found Exceptions
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> handleNotFoundException(NoResourceFoundException e) {
        log.warn("Resource Not Found: {}", e.getMessage());
        return Result.error(HttpStatus.NOT_FOUND.value(), ExceptionConstant.NOT_FOUND);
    }

    /**
     * Handle Unexpected Internal Server Errors
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e) {
        log.error("Internal Server Error: ", e);
        return Result.error(Result.DEFAULT_ERROR_CODE, ExceptionConstant.INTERNAL_SERVER_ERROR);
    }
}
