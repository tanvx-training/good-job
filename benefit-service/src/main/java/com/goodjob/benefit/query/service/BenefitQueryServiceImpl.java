package com.goodjob.benefit.query.service;

import com.goodjob.benefit.entity.Benefit;
import com.goodjob.benefit.exception.BenefitNotFoundException;
import com.goodjob.benefit.query.dto.BenefitView;
import com.goodjob.benefit.repository.BenefitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the BenefitQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BenefitQueryServiceImpl implements BenefitQueryService {

    private final BenefitRepository benefitRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<BenefitView> getAllBenefits() {
        log.info("Fetching all benefits");
        List<Benefit> benefits = benefitRepository.findAll();
        return mapToBenefitViews(benefits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public BenefitView getBenefitById(Integer id) {
        log.info("Fetching benefit with ID: {}", id);
        Benefit benefit = benefitRepository.findById(id)
                .orElseThrow(() -> new BenefitNotFoundException("Benefit not found with ID: " + id));
        return mapToBenefitView(benefit);
    }

    /**
     * Map a Benefit entity to a BenefitView.
     *
     * @param benefit the Benefit entity
     * @return the BenefitView
     */
    private BenefitView mapToBenefitView(Benefit benefit) {
        return BenefitView.builder()
                .id(benefit.getId())
                .type(benefit.getType())
                .build();
    }

    /**
     * Map a list of Benefit entities to a list of BenefitViews.
     *
     * @param benefits the list of Benefit entities
     * @return the list of BenefitViews
     */
    private List<BenefitView> mapToBenefitViews(List<Benefit> benefits) {
        return benefits.stream()
                .map(this::mapToBenefitView)
                .collect(Collectors.toList());
    }
} 