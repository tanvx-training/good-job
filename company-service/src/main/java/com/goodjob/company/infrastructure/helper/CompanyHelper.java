package com.goodjob.company.infrastructure.helper;

import com.goodjob.company.domain.company.dto.CompanyIndustryView;
import com.goodjob.company.domain.company.dto.CompanySpecialityView;

import java.util.List;

public interface CompanyHelper {

    List<CompanyIndustryView> getIndustries(List<Integer> idList);

    List<CompanySpecialityView> getSpecialities(List<Integer> idList);
}
