package com.goodjob.profile.application.security.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Converter for JWT tokens to extract roles and permissions.
 */
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
    return new JwtAuthenticationToken(jwt, authorities);
  }

  private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
    Map<String, Object> claims = jwt.getClaims();
    
    Collection<String> roles = getClaimAsList(claims, "roles");
    Collection<String> permissions = getClaimAsList(claims, "permissions");
    
    // Convert roles to authorities with ROLE_ prefix
    Collection<GrantedAuthority> roleAuthorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
    
    // Convert permissions to authorities
    Collection<GrantedAuthority> permissionAuthorities = permissions.stream()
        .map(permission -> new SimpleGrantedAuthority(permission))
        .collect(Collectors.toList());
    
    // Combine both roles and permissions
    return Stream.concat(roleAuthorities.stream(), permissionAuthorities.stream())
        .collect(Collectors.toList());
  }
  
  @SuppressWarnings("unchecked")
  private Collection<String> getClaimAsList(Map<String, Object> claims, String claimName) {
    Object claim = claims.get(claimName);
    if (claim == null) {
      return Collections.emptyList();
    }
    
    if (claim instanceof Collection) {
      return (Collection<String>) claim;
    }
    
    return Collections.emptyList();
  }
} 