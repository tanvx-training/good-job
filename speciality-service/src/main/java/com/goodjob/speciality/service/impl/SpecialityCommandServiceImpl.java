package com.goodjob.speciality.service.impl;

import com.goodjob.speciality.dto.SpecialityDto;
import com.goodjob.speciality.entity.Speciality;
import com.goodjob.speciality.exception.SpecialityAlreadyExistsException;
import com.goodjob.speciality.exception.SpecialityNotFoundException;
import com.goodjob.speciality.mapper.SpecialityMapper;
import com.goodjob.speciality.repository.SpecialityRepository;
import com.goodjob.speciality.service.SpecialityCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the SpecialityCommandService interface.
 * Following the CQRS pattern, this service handles all write operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialityCommandServiceImpl implements SpecialityCommandService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;

    @Override
    @Transactional
    public SpecialityDto createSpeciality(SpecialityDto specialityDto) {
        log.info("Creating new speciality with name: {}", specialityDto.getName());
        
        // Check if speciality with the same name already exists
        if (specialityRepository.existsByName(specialityDto.getName())) {
            log.warn("Speciality already exists with name: {}", specialityDto.getName());
            throw new SpecialityAlreadyExistsException("Speciality already exists with name: " + specialityDto.getName());
        }

        Speciality speciality = specialityMapper.toEntity(specialityDto);
        Speciality savedSpeciality = specialityRepository.save(speciality);
        log.info("Speciality created successfully with id: {}", savedSpeciality.getId());
        return specialityMapper.toDto(savedSpeciality);
    }

    @Override
    @Transactional
    public SpecialityDto updateSpeciality(Integer id, SpecialityDto specialityDto) {
        log.info("Updating speciality with id: {}", id);
        
        Speciality existingSpeciality = specialityRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Speciality not found with id: {}", id);
                    return new SpecialityNotFoundException("Speciality not found with id: " + id);
                });

        // Check if another speciality with the same name already exists
        if (!existingSpeciality.getName().equals(specialityDto.getName()) &&
                specialityRepository.existsByName(specialityDto.getName())) {
            log.warn("Another speciality already exists with name: {}", specialityDto.getName());
            throw new SpecialityAlreadyExistsException("Another speciality already exists with name: " + specialityDto.getName());
        }

        // Update the existing speciality
        existingSpeciality.setName(specialityDto.getName());
        existingSpeciality.setDescription(specialityDto.getDescription());

        Speciality updatedSpeciality = specialityRepository.save(existingSpeciality);
        log.info("Speciality updated successfully with id: {}", updatedSpeciality.getId());
        return specialityMapper.toDto(updatedSpeciality);
    }

    @Override
    @Transactional
    public void deleteSpeciality(Integer id) {
        log.info("Deleting speciality with id: {}", id);
        
        if (!specialityRepository.existsById(id)) {
            log.warn("Speciality not found with id: {}", id);
            throw new SpecialityNotFoundException("Speciality not found with id: " + id);
        }
        
        specialityRepository.deleteById(id);
        log.info("Speciality deleted successfully with id: {}", id);
    }
} 