package com.fastkart.productservice.exception;

import com.fastkart.commonlibrary.exception.ErrorResponse;
import com.fastkart.commonlibrary.exception.FastKartException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleFastKartException() {
        // Create a FastKartException
        FastKartException exception = new FastKartException("Test Exception", HttpStatus.NOT_FOUND.value(), "Not Found");

        // Call the handleFastKartException method
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleFastKartException(exception);

        // Verify the response status code and type
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
        assertEquals(ErrorResponse.class, Objects.requireNonNull(responseEntity.getBody()).getClass());
    }

    @Test
    public void testHandleException() {
        // Create a generic Exception
        Exception exception = new Exception("Test Exception");

        // Call the handleException method
        ErrorResponse errorResponse = globalExceptionHandler.handleException(exception);

        // Verify the response status code and type
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500);
    }
}
