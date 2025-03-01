package com.goodjob.job.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity representing salary information for a job posting.
 */
@Entity
@Table(name = "job_salaries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "job")
@ToString(exclude = "job")
public class JobSalary {

    @Id
    @Column(name = "job_id")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "min_salary")
    private BigDecimal minSalary;

    @Column(name = "max_salary")
    private BigDecimal maxSalary;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "salary_period", nullable = false)
    @Enumerated(EnumType.STRING)
    private SalaryPeriod salaryPeriod;

    @Column(name = "is_negotiable")
    private Boolean isNegotiable;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @PrePersist
    protected void onCreate() {
        if (isNegotiable == null) {
            isNegotiable = false;
        }
        if (isVisible == null) {
            isVisible = true;
        }
    }
} 