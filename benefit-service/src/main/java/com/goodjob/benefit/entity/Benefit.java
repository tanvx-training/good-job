package com.goodjob.benefit.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a job benefit.
 */
@Entity
@Table(name = "benefits", schema = "public", 
       indexes = {
           @Index(name = "public_benefits_pkey", columnList = "benefit_id", unique = true),
           @Index(name = "public_benefits_type_key", columnList = "type", unique = true)
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefits_benefit_id_seq")
    @SequenceGenerator(name = "benefits_benefit_id_seq", sequenceName = "benefits_benefit_id_seq", allocationSize = 1)
    @Column(name = "benefit_id")
    private Integer id;

    @Column(name = "type", unique = true, nullable = false)
    private String type;
} 