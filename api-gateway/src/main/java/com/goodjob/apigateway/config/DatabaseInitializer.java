package com.goodjob.apigateway.config;

import com.goodjob.apigateway.entity.Permission;
import com.goodjob.apigateway.entity.Role;
import com.goodjob.apigateway.entity.User;
import com.goodjob.apigateway.repository.PermissionRepository;
import com.goodjob.apigateway.repository.RoleRepository;
import com.goodjob.apigateway.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) {
    log.info("Initializing database with default roles and permissions");
    // 1️⃣ Danh sách permissions theo từng service
    List<String> permissionNames = List.of(
        // job-service
        "CREATE_JOB", "UPDATE_JOB", "DELETE_JOB", "READ_JOB",
        // company-service
        "CREATE_COMPANY", "UPDATE_COMPANY", "DELETE_COMPANY", "READ_COMPANY",
        // candidate-service
        "CREATE_CANDIDATE_PROFILE", "UPDATE_CANDIDATE_PROFILE", "DELETE_CANDIDATE_PROFILE",
        "READ_CANDIDATE_PROFILE",
        // posting-service
        "CREATE_JOB_POSTING", "UPDATE_JOB_POSTING", "DELETE_JOB_POSTING", "READ_JOB_POSTING",
        // industry-service
        "CREATE_INDUSTRY", "UPDATE_INDUSTRY", "DELETE_INDUSTRY", "READ_INDUSTRY",
        // benefit-service
        "CREATE_BENEFIT", "UPDATE_BENEFIT", "DELETE_BENEFIT", "READ_BENEFIT",
        // notification-service
        "SEND_NOTIFICATION", "READ_NOTIFICATION", "DELETE_NOTIFICATION",
        // skill-service
        "CREATE_SKILL", "UPDATE_SKILL", "DELETE_SKILL", "READ_SKILL",
        // speciality-service
        "CREATE_SPECIALITY", "UPDATE_SPECIALITY", "DELETE_SPECIALITY", "READ_SPECIALITY",
        // mail-service
        "SEND_EMAIL", "READ_EMAIL_LOGS", "DELETE_EMAIL_LOGS"
    );
// 2️⃣ Tạo permissions nếu chưa tồn tại
    List<Permission> permissions = permissionNames.stream()
        .map(permission -> permissionRepository.findByName(permission)
            .orElseGet(() -> permissionRepository.save(
                Permission.builder()
                    .name(permission)
                    .createdAt(LocalDateTime.now())
                    .deleteFlg(false)
                    .build())))
        .toList();

    // 3️⃣ Tạo roles
    List<Role> roles = List.of(
        createRoleIfNotExists("ROLE_ADMIN", new HashSet<>(permissions)), // Admin có tất cả quyền
        createRoleIfNotExists("ROLE_USER", Set.of()), // User không có quyền gì mặc định
        createRoleIfNotExists("ROLE_RECRUITER", Set.of()) // Nhà tuyển dụng không có quyền mặc định
    );

    // 4️⃣ Tạo tài khoản Admin mặc định
    if (userRepository.findByUsername("admin").isEmpty()) {
      User admin = new User();
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("password"));
      admin.setRoles(Set.of(roles.stream()
          .filter(role -> role.getName().equals("ROLE_ADMIN"))
          .findFirst().orElseThrow()));
      admin.setDeleteFlg(false);
      userRepository.save(admin);
      log.info("✅ Created default admin user: admin");
    }

    log.info("✅ Database initialization completed!");
  }

  private Role createRoleIfNotExists(String roleName, Set<Permission> permissions) {
    return roleRepository.findByName(roleName).orElseGet(() -> {
      Role role = Role.builder()
          .name(roleName)
          .permissions(permissions)
          .build();
      return roleRepository.save(role);
    });
  }
} 