package com.goodjob.speciality.query.service.impl;

import com.goodjob.speciality.entity.Speciality;
import com.goodjob.speciality.exception.SpecialityNotFoundException;
import com.goodjob.speciality.mapper.SpecialityMapper;
import com.goodjob.speciality.query.dto.SpecialityView;
import com.goodjob.speciality.query.service.SpecialityQueryService;
import com.goodjob.speciality.repository.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final SpecialityMapper specialityMapper;

    @Override
    public List<SpecialityView> getAllSpecialities() {
        log.info("Retrieving all specialities");
        List<Speciality> specialities = specialityRepository.findAll();
        log.info("Found {} specialities", specialities.size());
        return specialityMapper.toViewList(specialities);
    }

    @Override
    public SpecialityView getSpecialityById(Integer id) {
        log.info("Retrieving speciality with id: {}", id);
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Speciality not found with id: {}", id);
                    return new SpecialityNotFoundException("Speciality not found with id: " + id);
                });
        log.info("Found speciality with id: {}", id);
        return specialityMapper.toView(speciality);
    }

    @Override
    public SpecialityView getSpecialityByName(String name) {
        log.info("Retrieving speciality with name: {}", name);
        Speciality speciality = specialityRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warn("Speciality not found with name: {}", name);
                    return new SpecialityNotFoundException("Speciality not found with name: " + name);
                });
        log.info("Found speciality with name: {}", name);
        return specialityMapper.toView(speciality);
    }
} 