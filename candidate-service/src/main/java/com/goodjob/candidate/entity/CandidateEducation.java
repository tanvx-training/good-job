package com.goodjob.candidate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing an education record associated with a candidate.
 * Contains information about the degree, institution, field of study, and duration.
 */
@Entity
@Table(name = "candidate_educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, length = 100)
    private String institution;

    @Column(nullable = false, length = 100)
    private String degree;

    @Column(nullable = false, length = 100)
    private String fieldOfStudy;

    @Column(length = 100)
    private String location;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean currentlyStudying;

    private Double grade;

    @Column(length = 50)
    private String gradeScale;

    @Column(length = 1000)
    private String activities;

    @Column(length = 1000)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Returns a formatted string representation of the education period.
     *
     * @return the formatted period string (e.g., "2018 - 2022" or "2020 - Present")
     */
    @Transient
    public String getFormattedPeriod() {
        String startYear = String.valueOf(startDate.getYear());
        String endYear = currentlyStudying ? "Present" : String.valueOf(endDate.getYear());
        return startYear + " - " + endYear;
    }
} 