package com.goodjob.job.infrastructure.helper;

import com.goodjob.job.domain.job.dto.JobBenefitView;
import com.goodjob.job.domain.job.dto.JobCompanyView;
import com.goodjob.job.domain.job.dto.JobIndustryView;
import com.goodjob.job.domain.job.dto.JobSkillView;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface JobHelper {

    CompletableFuture<Set<JobBenefitView>> getBenefits(List<Integer> idList);

    CompletableFuture<Set<JobSkillView>> getSkills(List<Integer> idList);

    CompletableFuture<Set<JobIndustryView>> getIndustries(List<Integer> idList);

    CompletableFuture<JobCompanyView> getCompany(Integer companyId);
}
