package com.goodjob.apigateway.service.impl;

import com.goodjob.apigateway.dto.response.UserDTO;
import com.goodjob.apigateway.entity.Permission;
import com.goodjob.apigateway.entity.Role;
import com.goodjob.apigateway.entity.User;
import com.goodjob.apigateway.repository.RoleRepository;
import com.goodjob.apigateway.repository.UserRepository;
import com.goodjob.apigateway.service.UserService;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public UserDTO createUser(String username, String password) {
    User user = User.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .createdOn(LocalDateTime.now())
        .deleteFlg(false)
        .build();

    // Assign default USER role
    Role userRole = roleRepository.findByName("ROLE_USER")
        .orElseThrow(() -> new RuntimeException("Default role not found"));
    user.setRoles(Set.of(userRole));

    User savedUser = userRepository.save(user);
    return convertToDTO(savedUser);
  }

  @Override
  public UserDTO getUserDetails(String username) {
    return userRepository.findByUsername(username)
        .map(this::convertToDTO)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  private UserDTO convertToDTO(User user) {
    Set<String> roles = user.getRoles().stream()
        .map(Role::getName)
        .collect(Collectors.toSet());

    Set<String> permissions = user.getRoles().stream()
        .flatMap(role -> role.getPermissions().stream())
        .map(Permission::getName)
        .collect(Collectors.toSet());

    return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .roles(roles)
        .permissions(permissions)
        .enabled(!user.isDeleteFlg())
        .createdAt(user.getCreatedOn())
        .build();
  }
} 