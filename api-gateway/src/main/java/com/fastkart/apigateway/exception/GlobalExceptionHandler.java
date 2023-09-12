package com.fastkart.apigateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastkart.commonlibrary.exception.ErrorResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GlobalExceptionHandler {

    private GlobalExceptionHandler() {
    }

    public static Mono<Void> handleAuthorizationHeaderMissing(ServerWebExchange exchange) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Missing authorization header",
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized access to application"
        );
        return generateErrorResponse(exchange, errorResponse);
    }

    public static Mono<Void> handleGenericException(ServerWebExchange exchange, Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                Arrays.toString(e.getStackTrace())
        );
        return generateErrorResponse(exchange, errorResponse);
    }

    private static Mono<Void> generateErrorResponse(ServerWebExchange exchange, ErrorResponse errorResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        String errorResponseJson;
        try {
            errorResponseJson = objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            errorResponseJson = "{\"message\":\"Error while generating error response\" , \"errorCode\": \"500\", \"errorReason\": \"Internal Server Error\"}";
        }

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.valueOf(errorResponse.getErrorCode()));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        DataBuffer buffer = response.bufferFactory().wrap(errorResponseJson.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
