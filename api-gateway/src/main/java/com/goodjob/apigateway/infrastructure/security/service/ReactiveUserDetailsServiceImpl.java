package com.goodjob.apigateway.infrastructure.security.service;

import com.goodjob.apigateway.domain.entity.User;
import com.goodjob.apigateway.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

  private final UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return Mono.fromCallable(() -> userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)))
        .map(this::createUserDetails);
  }

  private UserDetails createUserDetails(User user) {
    // Combine roles and permissions into a flat list of authorities
    List<SimpleGrantedAuthority> authorities = Stream.concat(
        // Add role-based authorities (e.g., ROLE_ADMIN)
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName())),
        // Add permission-based authorities (e.g., READ_PROFILE)
        user.getRoles().stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
    ).collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        !user.isDeleteFlg(),
        true, // accountNonExpired
        true, // credentialsNonExpired
        true, // accountNonLocked
        authorities
    );
  }
}