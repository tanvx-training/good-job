package com.goodjob.metadata.domain.benefit.query;

import com.goodjob.metadata.domain.benefit.dto.BenefitQuery;
import com.goodjob.metadata.domain.benefit.dto.BenefitView;
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