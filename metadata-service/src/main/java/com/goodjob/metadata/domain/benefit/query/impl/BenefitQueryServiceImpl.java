package com.goodjob.metadata.domain.benefit.query.impl;

import com.goodjob.metadata.domain.benefit.entity.Benefit;
import com.goodjob.metadata.application.exception.BenefitNotFoundException;
import com.goodjob.metadata.domain.benefit.dto.BenefitQuery;
import com.goodjob.metadata.domain.benefit.dto.BenefitView;
import com.goodjob.metadata.domain.benefit.query.BenefitQueryService;
import com.goodjob.metadata.domain.benefit.repository.BenefitRepository;
import com.goodjob.common.dto.response.PageResponseDTO;
import java.util.List;
import java.util.Objects;
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

  @Override
  public List<BenefitView> getAllByIdList(List<Integer> idList) {
    List<Benefit> skillList = benefitRepository.findAllById(idList);
    if (!Objects.equals(idList.size(), skillList.size())) {
      List<Integer> existedIds = skillList
          .stream()
          .map(Benefit::getBenefitId)
          .toList();
      List<Integer> notExistedIds = idList.stream()
          .filter(id -> !existedIds.contains(id))
          .toList();
      throw new BenefitNotFoundException("Benefit not found with ids in: " + notExistedIds);
    }
    return skillList
        .stream()
        .map(this::mapToBenefitView)
        .toList();
  }

  private BenefitView mapToBenefitView(Benefit benefit) {

    return BenefitView.builder()
        .id(benefit.getBenefitId())
        .type(benefit.getType())
        .createdOn(benefit.getCreatedOn())
        .createdBy(benefit.getCreatedBy())
        .lastModifiedOn(benefit.getLastModifiedOn())
        .lastModifiedBy(benefit.getLastModifiedBy())
        .deleteFlg(benefit.isDeleteFlg())
        .build();
  }
} 