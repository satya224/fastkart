package com.fastkart.authservice.exception;

import com.fastkart.commonlibrary.exception.ErrorResponse;
import com.fastkart.commonlibrary.exception.FastKartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        return new ErrorResponse(badCredentialsException.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                Arrays.toString(badCredentialsException.getStackTrace()));
    }
}