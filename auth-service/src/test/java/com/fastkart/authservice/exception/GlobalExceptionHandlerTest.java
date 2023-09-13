package com.fastkart.authservice.exception;

import com.fastkart.commonlibrary.exception.ErrorResponse;
import com.fastkart.commonlibrary.exception.FastKartException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleFastKartException() {
        FastKartException fastKartException = new FastKartException("Test exception", HttpStatus.BAD_REQUEST.value(), "Error reason");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleFastKartException(fastKartException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assert errorResponse != null;
        assertEquals("Test exception", errorResponse.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getErrorCode());
        assertEquals("Error reason", errorResponse.getErrorReason());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Illegal argument");
        ErrorResponse errorResponse = globalExceptionHandler.handleIllegalArgumentException(illegalArgumentException);

        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getErrorCode());
        assertEquals("Illegal argument", errorResponse.getMessage());
    }

    @Test
    public void testHandleJWTException() {
        JwtException jwtException = new JwtException("JWT exception");
        ErrorResponse errorResponse = globalExceptionHandler.handleJWTException(jwtException);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), errorResponse.getErrorCode());
        assertEquals("JWT exception", errorResponse.getMessage());
    }

    @Test
    public void testHandleBadCredentialsException() {
        BadCredentialsException badCredentialsException = new BadCredentialsException("Bad credentials");
        ErrorResponse errorResponse = globalExceptionHandler.handleBadCredentialsException(badCredentialsException);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), errorResponse.getErrorCode());
        assertEquals("Bad credentials", errorResponse.getMessage());
    }

    @Test
    public void testHandleException() {
        Exception exception = new Exception("Internal server error");
        ErrorResponse errorResponse = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getErrorCode());
        assertEquals("Internal server error", errorResponse.getMessage());
    }
}
