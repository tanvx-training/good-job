package com.goodjob.metadata.query.service;

import com.goodjob.metadata.query.dto.BenefitQuery;
import com.goodjob.metadata.query.dto.BenefitView;
import com.goodjob.common.dto.response.PageResponseDTO;
import java.util.List;

/**
 * Service interface for benefit query operations.
 */
public interface BenefitQueryService {

  PageResponseDTO<BenefitView> getAllBenefits(BenefitQuery query);

  BenefitView getBenefitById(Integer id);

  List<BenefitView> getAllByIdList(List<Integer> idList);
}