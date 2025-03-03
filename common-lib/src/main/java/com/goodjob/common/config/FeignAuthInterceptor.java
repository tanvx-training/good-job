package com.goodjob.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Interceptor for Feign clients to propagate authentication tokens.
 * Automatically adds the Authorization header to outgoing requests.
 */
@Slf4j
public class FeignAuthInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            log.debug("No authentication found in SecurityContext");
            return;
        }
        
        // Get the Authorization header from the current request context
        // This is a simplified approach - in a real application, you would extract the token
        // from the authentication object based on your authentication mechanism
        String token = authentication.getCredentials() != null ? 
                authentication.getCredentials().toString() : null;
                
        if (token != null && !token.isEmpty()) {
            template.header(AUTHORIZATION_HEADER, token);
            log.debug("Added authentication token to Feign request");
        } else {
            log.debug("No token available in the authentication object");
        }
    }
} 