package com.goodjob.company.domain.company.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing metrics for a company.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "company_metrics")
public class CompanyMetric extends BaseEntity {
    
    @Id
    @Column(name = "company_metric_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyMetricId;
    
    @Column(name = "employee_count")
    private Integer employeeCount;
    
    @Column(name = "follower_count")
    private Integer followerCount;
    
    @Column(name = "recorded_on")
    private Long recordOn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
} 