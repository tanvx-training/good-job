package com.goodjob.job.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing salary information for a job posting.
 */
@Table(name = "job_salaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class JobSalary extends BaseEntity {

    @Id
    @Column(name = "salary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salaryId;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "min_salary")
    private BigDecimal minSalary;

    @Column(name = "med_salary")
    private BigDecimal medSalary;

    @Column(name = "max_salary")
    private BigDecimal maxSalary;

    @Column(name = "pay_period", nullable = false)
    private Integer payPeriod;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "compensation_type")
    private Integer compensationType;
} 