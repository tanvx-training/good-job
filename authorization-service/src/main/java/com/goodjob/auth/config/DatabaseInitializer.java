package com.goodjob.auth.config;

import com.goodjob.auth.entity.Role;
import com.goodjob.auth.entity.User;
import com.goodjob.auth.repository.RoleRepository;
import com.goodjob.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Database initializer to set up default roles and admin user.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Initialize the database with default roles and admin user.
     *
     * @param args command line arguments
     */
    @Override
    @Transactional
    public void run(String... args) {
        initRoles();
        initAdminUser();
    }

    /**
     * Initialize default roles.
     */
    private void initRoles() {
        if (roleRepository.count() == 0) {
            log.info("Initializing roles...");
            
            Role userRole = Role.builder()
                    .name("USER")
                    .description("Regular user role with basic permissions")
                    .createdAt(LocalDateTime.now())
                    .build();
            
            Role adminRole = Role.builder()
                    .name("ADMIN")
                    .description("Administrator role with full permissions")
                    .createdAt(LocalDateTime.now())
                    .build();
            
            Role recruiterRole = Role.builder()
                    .name("RECRUITER")
                    .description("Recruiter role with job posting permissions")
                    .createdAt(LocalDateTime.now())
                    .build();
            
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            roleRepository.save(recruiterRole);
            
            log.info("Roles initialized successfully");
        }
    }

    /**
     * Initialize admin user.
     */
    private void initAdminUser() {
        if (userRepository.count() == 0) {
            log.info("Initializing admin user...");
            
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("User role not found"));
            
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);
            
            User adminUser = User.builder()
                    .email("admin@goodjob.com")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("Admin")
                    .lastName("User")
                    .headline("System Administrator")
                    .summary("System administrator for the Good Job platform")
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .roles(adminRoles)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            userRepository.save(adminUser);
            
            log.info("Admin user initialized successfully");
        }
    }
} 