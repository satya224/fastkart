package com.fastkart.authservice.exception;

import com.fastkart.commonlibrary.exception.ErrorResponse;
import com.fastkart.commonlibrary.exception.FastKartException;
import io.jsonwebtoken.JwtException;
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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return new ErrorResponse(illegalArgumentException.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                Arrays.toString(illegalArgumentException.getStackTrace()));
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleJWTException(JwtException jwtException) {
        return new ErrorResponse(jwtException.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                Arrays.toString(jwtException.getStackTrace()));
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        return new ErrorResponse(badCredentialsException.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                Arrays.toString(badCredentialsException.getStackTrace()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        return new ErrorResponse(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Arrays.toString(exception.getStackTrace()));
    }
}
