package com.goodjob.metadata.domain.speciality.query.impl;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.metadata.domain.speciality.entity.Speciality;
import com.goodjob.metadata.domain.speciality.dto.SpecialityQuery;
import com.goodjob.metadata.domain.speciality.dto.SpecialityView;
import com.goodjob.metadata.domain.speciality.query.SpecialityQueryService;
import com.goodjob.metadata.domain.speciality.repository.SpecialityRepository;
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
 * Implementation of the SpecialityQueryService interface.
 * Following the CQRS pattern, this service handles all read operations.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SpecialityQueryServiceImpl implements SpecialityQueryService {

    private final SpecialityRepository specialityRepository;

    @Override
    public PageResponseDTO<SpecialityView> getAllSpecialities(SpecialityQuery query) {
        log.info("Retrieving all specialities");
        String[] parts = query.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);
        return new PageResponseDTO<>(specialityRepository.findAllByDeleteFlg(false, pageable)
                .map(this::mapToSpecialityView));
    }

    @Override
    public SpecialityView getSpecialityById(Integer id) {
        log.info("Retrieving speciality with id: {}", id);
        return specialityRepository.findById(id)
                .map(this::mapToSpecialityView)
                .orElseThrow(() -> {
                    log.warn("Speciality not found with id: {}", id);
                    return new ResourceNotFoundException(Speciality.class.getName(), "ID", id);
                });
    }

    @Override
    public SpecialityView getSpecialityByName(String name) {
        log.info("Retrieving speciality with name: {}", name);
        return specialityRepository.findBySpecialityName(name)
                .map(this::mapToSpecialityView)
                .orElseThrow(() -> {
                    log.warn("Speciality not found with name: {}", name);
                    return new ResourceNotFoundException(Speciality.class.getName(), "Name", name);
                });
    }

    @Override
    public List<SpecialityView> getAllByIdList(List<Integer> idList) {
        List<Speciality> industryList = specialityRepository.findAllById(idList);
        if (!Objects.equals(idList.size(), industryList.size())) {
            List<Integer> existedIds = industryList
                    .stream()
                    .map(Speciality::getSpecialityId)
                    .toList();
            List<Integer> notExistedIds = idList.stream()
                    .filter(id -> !existedIds.contains(id))
                    .toList();
            throw new ResourceNotFoundException(Speciality.class.getName(), "ID", String.valueOf(notExistedIds));
        }
        return industryList
                .stream()
                .map(this::mapToSpecialityView)
                .toList();
    }

    private SpecialityView mapToSpecialityView(Speciality speciality) {
        return SpecialityView.builder()
                .id(speciality.getSpecialityId())
                .name(speciality.getSpecialityName())
                .createdOn(speciality.getCreatedOn())
                .createdBy(speciality.getCreatedBy())
                .lastModifiedOn(speciality.getLastModifiedOn())
                .lastModifiedBy(speciality.getLastModifiedBy())
                .deleteFlg(speciality.isDeleteFlg())
                .build();
    }
} 