package com.goodjob.apigateway.security.filter;

import com.goodjob.apigateway.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter, Ordered {

  private final JwtTokenProvider jwtTokenProvider;
  private final List<String> excludedUrls = List.of(
      "/api/v1/auth/login",
      "/api/v1/auth/register",
      "/api/v1/auth/refresh"
  );

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    // Skip authentication for excluded URLs
    if (isUrlExcluded(request.getURI().getPath())) {
      return chain.filter(exchange);
    }

    // Check for Authorization header
    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
      return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
    }

    // Get token from header
    String token = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    } else {
      return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
    }

    // Validate token
    if (!jwtTokenProvider.validateToken(token)) {
      return onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
    }

    // Extract user details and add to headers
    String username = jwtTokenProvider.getUsernameFromToken(token);
    Claims claims = jwtTokenProvider.getClaimsFromToken(token);
    List<String> roles = (List<String>) claims.get("roles");
    List<String> permissions = (List<String>) claims.get("permissions");
    // Add user details to request headers for downstream services
    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
        .header("X-Auth-Username", username)
        .header("X-Auth-Roles", String.join(",", roles))
        .header("X-Auth-Permissions", String.join(",", permissions))
        .build();

    log.info("{}", modifiedRequest.getHeaders());
    return chain.filter(exchange.mutate().request(modifiedRequest).build());
  }

  private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
    log.error("Authentication error: {}", error);
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);
    return response.setComplete();
  }

  private boolean isUrlExcluded(String url) {
    return excludedUrls.stream().anyMatch(url::startsWith);
  }

  @Override
  public int getOrder() {
    return -100; // High priority
  }
} 