package com.goodjob.metadata.domain.speciality.command.impl;

import com.goodjob.common.exception.ResourceExistedException;
import com.goodjob.common.exception.ResourceNotFoundException;
import com.goodjob.metadata.domain.speciality.dto.CreateSpecialityCommand;
import com.goodjob.metadata.domain.speciality.dto.UpdateSpecialityCommand;
import com.goodjob.metadata.domain.speciality.command.SpecialityCommandService;
import com.goodjob.metadata.domain.speciality.entity.Speciality;
import com.goodjob.metadata.domain.speciality.repository.SpecialityRepository;
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
            throw new ResourceExistedException(Speciality.class.getName(), "Name", command.getName());
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
                    return new ResourceNotFoundException(Speciality.class.getName(), "ID", id);
                });

        // Check if another speciality with the same name already exists
        if (!origin.getSpecialityName().equals(command.getName()) &&
                specialityRepository.existsBySpecialityName(command.getName())) {
            log.warn("Another speciality already exists with name: {}", command.getName());
            throw new ResourceExistedException(Speciality.class.getName(), "Name", command.getName());
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
            throw new ResourceNotFoundException(Speciality.class.getName(), "ID", id);
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