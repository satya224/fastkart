package com.fastkart.apigateway.exception;

import com.fastkart.commonlibrary.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        return new ErrorResponse(exception.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        Arrays.toString(exception.getStackTrace()));
    }
}
