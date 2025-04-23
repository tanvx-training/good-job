package com.goodjob.apigateway.application.dto.response;

import java.time.LocalDateTime;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;
  private String username;
  private Set<String> roles;
  private Set<String> permissions;
  private boolean enabled;
  private LocalDateTime createdAt;
}