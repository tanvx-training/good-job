package com.goodjob.auth.service;

import com.goodjob.auth.dto.UserDto;
import com.goodjob.auth.entity.Role;
import com.goodjob.auth.entity.User;
import com.goodjob.auth.repository.RoleRepository;
import com.goodjob.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for user management operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Get all users.
     *
     * @return list of all users
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a user by ID.
     *
     * @param id the user ID
     * @return the user
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    /**
     * Get the current authenticated user.
     *
     * @return the current user
     */
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }

    /**
     * Update a user.
     *
     * @param id the user ID
     * @param userDto the updated user data
     * @return the updated user
     */
    @Transactional
    public User updateUser(Long id, UserDto userDto) {
        User user = getUserById(id);
        
        // Update basic information
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setHeadline(userDto.getHeadline());
        user.setSummary(userDto.getSummary());
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());
        user.setEnabled(userDto.isEnabled());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Update roles if provided and user is an admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty() && 
                authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : userDto.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
            user.setRoles(roles);
        }
        
        return userRepository.save(user);
    }

    /**
     * Delete a user.
     *
     * @param id the user ID
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
} 