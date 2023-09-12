package com.fastkart.productservice.exception;

import com.fastkart.commonlibrary.exception.ErrorResponse;
import com.fastkart.commonlibrary.exception.FastKartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FastKartException.class)
    public ResponseEntity<ErrorResponse> handleFastKartException(FastKartException fastKartException) {
        return ResponseEntity.status(fastKartException.getErrorCode())
                .body(new ErrorResponse(fastKartException.getMessage(),
                        fastKartException.getErrorCode(),
                        fastKartException.getErrorReason()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        return new ErrorResponse(exception.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        Arrays.toString(exception.getStackTrace()));
    }
}
