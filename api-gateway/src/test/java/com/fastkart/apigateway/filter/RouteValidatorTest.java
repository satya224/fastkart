package com.fastkart.apigateway.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RouteValidatorTest {


    @Mock
    private ServerHttpRequest serverHttpRequest;

    private RouteValidator routeValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        routeValidator = new RouteValidator();
    }

    @Test
    public void testIsSecuredWithSecuredRoute() {
        when(serverHttpRequest.getURI()).thenReturn(URI.create("/secure-endpoint"));
        Predicate<ServerHttpRequest> isSecured = routeValidator.isSecured;
        assertTrue(isSecured.test(serverHttpRequest));
    }

    @Test
    public void testIsSecuredWithOpenApiEndpoint() {
        when(serverHttpRequest.getURI()).thenReturn(URI.create("/auth"));
        Predicate<ServerHttpRequest> isSecured = routeValidator.isSecured;
        assertFalse(isSecured.test(serverHttpRequest));
    }

}