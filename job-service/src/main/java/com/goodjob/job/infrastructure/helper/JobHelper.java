package com.goodjob.job.infrastructure.helper;

import com.goodjob.job.domain.job.dto.JobBenefitView;
import com.goodjob.job.domain.job.dto.JobCompanyView;
import com.goodjob.job.domain.job.dto.JobIndustryView;
import com.goodjob.job.domain.job.dto.JobSkillView;

import java.util.List;
import java.util.Set;

public interface JobHelper {

    Set<JobBenefitView> getBenefits(List<Integer> idList);

    Set<JobSkillView> getSkills(List<Integer> idList);

    Set<JobIndustryView> getIndustries(List<Integer> idList);

    JobCompanyView getCompany(Integer companyId);
}
