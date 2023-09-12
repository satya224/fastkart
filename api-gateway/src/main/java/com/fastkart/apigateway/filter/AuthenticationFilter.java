package com.fastkart.apigateway.filter;

import com.fastkart.commonlibrary.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static com.fastkart.apigateway.exception.GlobalExceptionHandler.handleAuthorizationHeaderMissing;
import static com.fastkart.apigateway.exception.GlobalExceptionHandler.handleGenericException;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RestTemplate restTemplate;
    private final RouteValidator validator;


    public AuthenticationFilter(RouteValidator validator, RestTemplate restTemplate) {
        super(Config.class);
        this.validator = validator;
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info(String.valueOf(validator.isSecured.test(exchange.getRequest())));
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

                if (authHeaders == null || authHeaders.isEmpty()) {
                    return handleAuthorizationHeaderMissing(exchange);
                }

                String authHeader = authHeaders.get(0);

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
                    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                    String url = "http://localhost:8081/user/validate";
                    ResponseEntity<UserDto> userResponse = restTemplate.exchange(
                            url, HttpMethod.GET, requestEntity, UserDto.class);
                    UserDto user = Objects.requireNonNull(userResponse.getBody());
                    exchange.getRequest().mutate()
                            .header("userId", user.getUserId().toString())
                            .header("role", user.getRole())
                            .build();

                } catch (Exception e) {
                    return handleGenericException(exchange, e);
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }


}