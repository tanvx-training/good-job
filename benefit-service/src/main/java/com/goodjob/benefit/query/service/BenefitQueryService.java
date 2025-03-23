package com.goodjob.benefit.query.service;

import com.goodjob.benefit.query.dto.BenefitQuery;
import com.goodjob.benefit.query.dto.BenefitView;
import com.goodjob.common.dto.PageResponseDTO;
import java.util.List;

/**
 * Service interface for benefit query operations.
 */
public interface BenefitQueryService {

  PageResponseDTO<BenefitView> getAllBenefits(BenefitQuery query);

  BenefitView getBenefitById(Integer id);

  List<BenefitView> getAllByIdList(List<Integer> idList);
}