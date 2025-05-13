package com.goodjob.profile.infrastructure.helper;

import com.goodjob.profile.domain.client.ExperienceCompanyView;
import com.goodjob.profile.domain.client.ProfileSkillView;

import java.util.List;

public interface ProfileHelper {

    List<ProfileSkillView> getSkills(List<Integer> idList);

    List<ExperienceCompanyView> getBatchCompanies(List<Integer> companyIds);
}
