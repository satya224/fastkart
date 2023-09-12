package com.fastkart.apigateway.filter;

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
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = authHeaders.get(0);

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
                    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                    String url="http://localhost:8081/user/validate";
                    ResponseEntity<Integer> userIdResponse = restTemplate.exchange(
                            url, HttpMethod.GET, requestEntity, Integer.class);
                    String userId = Objects.requireNonNull(userIdResponse.getBody()).toString();
                    exchange.getRequest().mutate().header("userId", userId).build();

                } catch (Exception e) {
                    throw new RuntimeException("unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}