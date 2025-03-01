package com.goodjob.candidate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Entity representing a work experience associated with a candidate.
 * Contains information about the job title, company, duration, and responsibilities.
 */
@Entity
@Table(name = "candidate_experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, length = 100)
    private String jobTitle;

    @Column(nullable = false, length = 100)
    private String company;

    @Column(length = 100)
    private String location;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean currentlyWorking;

    @Column(length = 5000)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Calculates the duration of the experience in months.
     *
     * @return the duration in months
     */
    @Transient
    public int getDurationInMonths() {
        LocalDate end = currentlyWorking ? LocalDate.now() : endDate;
        if (end == null) {
            return 0;
        }
        Period period = Period.between(startDate, end);
        return period.getYears() * 12 + period.getMonths();
    }

    /**
     * Returns a formatted string representation of the duration.
     *
     * @return the formatted duration string (e.g., "2 years 3 months")
     */
    @Transient
    public String getFormattedDuration() {
        int months = getDurationInMonths();
        int years = months / 12;
        int remainingMonths = months % 12;

        StringBuilder duration = new StringBuilder();
        if (years > 0) {
            duration.append(years).append(years == 1 ? " year" : " years");
        }
        if (remainingMonths > 0) {
            if (duration.length() > 0) {
                duration.append(" ");
            }
            duration.append(remainingMonths).append(remainingMonths == 1 ? " month" : " months");
        }
        return duration.toString();
    }
} 