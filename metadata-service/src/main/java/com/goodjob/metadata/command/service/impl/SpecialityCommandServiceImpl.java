package com.goodjob.metadata.command.service.impl;

import com.goodjob.metadata.command.dto.CreateSpecialityCommand;
import com.goodjob.metadata.command.dto.UpdateSpecialityCommand;
import com.goodjob.metadata.command.service.SpecialityCommandService;
import com.goodjob.metadata.entity.Speciality;
import com.goodjob.metadata.exception.SpecialityAlreadyExistsException;
import com.goodjob.metadata.exception.SpecialityNotFoundException;
import com.goodjob.metadata.repository.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of the SpecialityCommandService interface.
 * Following the CQRS pattern, this service handles all write operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialityCommandServiceImpl implements SpecialityCommandService {

    private final SpecialityRepository specialityRepository;

    @Override
    @Transactional
    public Integer createSpeciality(CreateSpecialityCommand command) {
        log.info("Creating new speciality with name: {}", command.getName());
        
        // Check if speciality with the same name already exists
        if (specialityRepository.existsBySpecialityName(command.getName())) {
            log.warn("Speciality already exists with name: {}", command.getName());
            throw new SpecialityAlreadyExistsException("Speciality already exists with name: " + command.getName());
        }

        Speciality speciality = toEntityForCreate(command);
        specialityRepository.save(speciality);
        return speciality.getSpecialityId();
    }

    @Override
    @Transactional
    public void updateSpeciality(Integer id, UpdateSpecialityCommand command) {
        log.info("Updating speciality with id: {}", id);
        
        Speciality origin = specialityRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Speciality not found with id: {}", id);
                    return new SpecialityNotFoundException("Speciality not found with id: " + id);
                });

        // Check if another speciality with the same name already exists
        if (!origin.getSpecialityName().equals(command.getName()) &&
                specialityRepository.existsBySpecialityName(command.getName())) {
            log.warn("Another speciality already exists with name: {}", command.getName());
            throw new SpecialityAlreadyExistsException("Another speciality already exists with name: " + command.getName());
        }

        // Update the existing speciality
        mergeEntityForUpdate(origin, command);
        specialityRepository.save(origin);
        log.info("Speciality updated successfully with id: {}", origin.getSpecialityId());
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

    private Speciality toEntityForCreate(CreateSpecialityCommand command) {
        return Speciality.builder()
                .specialityName(command.getName())
                .createdOn(LocalDateTime.now())
                .deleteFlg(false)
                .build();
    }

    private void mergeEntityForUpdate(Speciality origin, UpdateSpecialityCommand command) {
        origin.setSpecialityName(command.getName());
        origin.setLastModifiedOn(LocalDateTime.now());
    }
} 