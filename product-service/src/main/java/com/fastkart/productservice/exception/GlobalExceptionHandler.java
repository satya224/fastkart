package com.fastkart.productservice.exception;

import com.fastkart.commonlibrary.exception.ErrorResponse;
import com.fastkart.commonlibrary.exception.FastKartException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(ex.getMessage(),
                        status.value(),
                        Arrays.toString(ex.getStackTrace())));
    }

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
