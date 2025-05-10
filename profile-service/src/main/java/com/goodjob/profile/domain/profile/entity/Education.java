package com.goodjob.profile.domain.profile.entity;

import com.goodjob.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing an education entry.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "educations")
public class Education extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long educationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;

    @Column(name = "current", nullable = false)
    private Boolean current;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
} 