package com.goodjob.industry.query.service;

import com.goodjob.industry.entity.Industry;
import com.goodjob.industry.exception.IndustryNotFoundException;
import com.goodjob.industry.mapper.IndustryMapper;
import com.goodjob.industry.query.dto.IndustryView;
import com.goodjob.industry.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the IndustryQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IndustryQueryServiceImpl implements IndustryQueryService {

    private final IndustryRepository industryRepository;
    private final IndustryMapper industryMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<IndustryView> getAllIndustries() {
        log.info("Fetching all industries");
        List<Industry> industries = industryRepository.findAll();
        return industries.stream()
                .map(industryMapper::toView)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public IndustryView getIndustryById(Integer id) {
        log.info("Fetching industry with ID: {}", id);
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new IndustryNotFoundException("Industry not found with ID: " + id));
        return industryMapper.toView(industry);
    }
} 