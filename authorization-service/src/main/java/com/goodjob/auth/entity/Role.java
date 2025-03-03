package com.goodjob.auth.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a user role in the system.
 * Roles are used for authorization and access control.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;
} 