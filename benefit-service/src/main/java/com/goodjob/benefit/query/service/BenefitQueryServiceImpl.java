package com.goodjob.benefit.query.service;

import com.goodjob.benefit.entity.Benefit;
import com.goodjob.benefit.exception.BenefitNotFoundException;
import com.goodjob.benefit.query.dto.BenefitQuery;
import com.goodjob.benefit.query.dto.BenefitView;
import com.goodjob.benefit.repository.BenefitRepository;
import com.goodjob.common.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the BenefitQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BenefitQueryServiceImpl implements BenefitQueryService {

  private final BenefitRepository benefitRepository;

  @Override
  @Transactional(readOnly = true)
  public PageResponseDTO<BenefitView> getAllBenefits(BenefitQuery query) {

    log.info("Fetching all benefits");

    String[] parts = query.getSort().split(",");
    Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
    Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

    return new PageResponseDTO<>(benefitRepository.findAll(pageable)
        .map(this::mapToBenefitView));
  }

  @Override
  @Transactional(readOnly = true)
  public BenefitView getBenefitById(Integer id) {

    log.info("Fetching benefit with ID: {}", id);

    Benefit benefit = benefitRepository.findById(id)
        .orElseThrow(() -> new BenefitNotFoundException("Benefit not found with ID: " + id));

    return mapToBenefitView(benefit);
  }

  private BenefitView mapToBenefitView(Benefit benefit) {

    return BenefitView.builder()
        .id(benefit.getBenefitId())
        .type(benefit.getType())
        .build();
  }
} 