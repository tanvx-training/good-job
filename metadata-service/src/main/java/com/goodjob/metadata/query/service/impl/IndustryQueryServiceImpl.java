package com.goodjob.metadata.query.service.impl;

import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.metadata.entity.Industry;
import com.goodjob.metadata.exception.IndustryNotFoundException;
import com.goodjob.metadata.query.dto.IndustryQuery;
import com.goodjob.metadata.query.dto.IndustryView;
import com.goodjob.metadata.query.service.IndustryQueryService;
import com.goodjob.metadata.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of the IndustryQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IndustryQueryServiceImpl implements IndustryQueryService {

    private final IndustryRepository industryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<IndustryView> getAllIndustries(IndustryQuery query) {
        log.info("Fetching all industries");
        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);
        return new PageResponseDTO<>(industryRepository.findAllByDeleteFlg(false, pageable)
                .map(this::mapToIndustryView));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public IndustryView getIndustryById(Integer id) {
        log.info("Fetching industry with ID: {}", id);
        return industryRepository.findById(id)
                .map(this::mapToIndustryView)
                .orElseThrow(() -> new IndustryNotFoundException("Industry not found with ID: " + id));
    }

    @Override
    public List<IndustryView> getAllByIdList(List<Integer> idList) {
        List<Industry> industryList = industryRepository.findAllById(idList);
        if (!Objects.equals(idList.size(), industryList.size())) {
            List<Integer> existedIds = industryList
                    .stream()
                    .map(Industry::getIndustryId)
                    .toList();
            List<Integer> notExistedIds = idList.stream()
                    .filter(id -> !existedIds.contains(id))
                    .toList();
            throw new IndustryNotFoundException("Industry not found with ids in: " + notExistedIds);
        }
        return industryList
                .stream()
                .map(this::mapToIndustryView)
                .toList();
    }

    private IndustryView mapToIndustryView(Industry industry) {
        return IndustryView.builder()
                .id(industry.getIndustryId())
                .name(industry.getIndustryName())
                .createdOn(industry.getCreatedOn())
                .createdBy(industry.getCreatedBy())
                .lastModifiedOn(industry.getLastModifiedOn())
                .lastModifiedBy(industry.getLastModifiedBy())
                .deleteFlg(industry.isDeleteFlg())
                .build();
    }
} 