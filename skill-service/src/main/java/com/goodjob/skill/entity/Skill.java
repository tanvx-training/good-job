package com.goodjob.skill.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a skill.
 */
@Entity
@Table(name = "skills", schema = "public", 
       indexes = {
           @Index(name = "public_skills_pkey", columnList = "skill_id", unique = true),
           @Index(name = "public_skills_skill_abr_key", columnList = "skill_abr", unique = true)
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_skill_id_seq")
    @SequenceGenerator(name = "skills_skill_id_seq", sequenceName = "skills_skill_id_seq", allocationSize = 1)
    @Column(name = "skill_id")
    private Integer id;

    @Column(name = "skill_abr", unique = true, nullable = false)
    private String abbreviation;

    @Column(name = "skill_name", nullable = false)
    private String name;
} 