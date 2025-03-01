package com.goodjob.candidate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a project associated with a candidate.
 * Contains information about the project name, description, duration, and links.
 */
@Entity
@Table(name = "candidate_projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(length = 255)
    private String projectUrl;

    @Column(length = 255)
    private String repositoryUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean currentlyWorking;

    @Column(length = 1000)
    private String technologies;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Returns a formatted string representation of the project period.
     *
     * @return the formatted period string (e.g., "Jan 2022 - Mar 2022" or "Jun 2023 - Present")
     */
    @Transient
    public String getFormattedPeriod() {
        if (startDate == null) {
            return "";
        }
        
        String start = startDate.getMonth().toString().substring(0, 3) + " " + startDate.getYear();
        String end = currentlyWorking ? "Present" : 
                     (endDate != null ? endDate.getMonth().toString().substring(0, 3) + " " + endDate.getYear() : "");
        
        return start + (end.isEmpty() ? "" : " - " + end);
    }
} 