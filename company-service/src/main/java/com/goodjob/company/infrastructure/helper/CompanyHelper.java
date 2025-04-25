package com.goodjob.company.infrastructure.helper;

import com.goodjob.company.domain.company.dto.CompanyIndustryView;
import com.goodjob.company.domain.company.dto.CompanySpecialityView;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface CompanyHelper {

    CompletableFuture<Set<CompanyIndustryView>> getIndustries(List<Integer> idList);

    CompletableFuture<Set<CompanySpecialityView>> getSpecialities(List<Integer> idList);
}
