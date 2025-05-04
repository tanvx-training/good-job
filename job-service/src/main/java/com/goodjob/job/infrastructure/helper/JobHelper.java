package com.goodjob.job.infrastructure.helper;

import com.goodjob.job.domain.job.dto.JobBenefitView;
import com.goodjob.job.domain.job.dto.JobCompanyView;
import com.goodjob.job.domain.job.dto.JobIndustryView;
import com.goodjob.job.domain.job.dto.JobSkillView;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface JobHelper {

    List<JobBenefitView> getBenefits(List<Integer> idList);

    List<JobSkillView> getSkills(List<Integer> idList);

    List<JobIndustryView> getIndustries(List<Integer> idList);

    JobCompanyView getCompany(Integer companyId);
}
