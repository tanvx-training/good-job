package com.goodjob.auth.repository;

import com.goodjob.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity operations.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by name.
     *
     * @param name the role name to search for
     * @return an Optional containing the role if found, or empty if not found
     */
    Optional<Role> findByName(String name);

    /**
     * Check if a role exists with the given name.
     *
     * @param name the role name to check
     * @return true if a role exists with the name, false otherwise
     */
    boolean existsByName(String name);
} 