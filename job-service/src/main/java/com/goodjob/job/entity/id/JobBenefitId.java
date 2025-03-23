package com.goodjob.job.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Composite key for the JobBenefit entity.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobBenefitId implements Serializable {

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "benefit_id")
    private Integer benefitId;
}