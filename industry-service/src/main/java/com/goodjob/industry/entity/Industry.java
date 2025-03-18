package com.goodjob.industry.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing an industry category.
 */
@Table(name = "industries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Industry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "industry_id")
    private Integer industryId;

    @Column(name = "industry_name", unique = true, nullable = false)
    private String industryName;
} 