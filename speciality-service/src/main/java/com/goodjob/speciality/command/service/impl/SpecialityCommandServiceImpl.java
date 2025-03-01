package com.goodjob.speciality.command.service.impl;

import com.goodjob.speciality.command.dto.CreateSpecialityCommand;
import com.goodjob.speciality.command.dto.UpdateSpecialityCommand;
import com.goodjob.speciality.command.service.SpecialityCommandService;
import com.goodjob.speciality.entity.Speciality;
import com.goodjob.speciality.exception.SpecialityAlreadyExistsException;
import com.goodjob.speciality.exception.SpecialityNotFoundException;
import com.goodjob.speciality.mapper.SpecialityMapper;
import com.goodjob.speciality.repository.SpecialityRepository;
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
    public Integer createSpeciality(CreateSpecialityCommand command) {
        log.info("Creating new speciality with name: {}", command.getName());
        
        // Check if speciality with the same name already exists
        if (specialityRepository.existsByName(command.getName())) {
            log.warn("Speciality already exists with name: {}", command.getName());
            throw new SpecialityAlreadyExistsException("Speciality already exists with name: " + command.getName());
        }

        Speciality speciality = specialityMapper.toEntity(command);
        Speciality savedSpeciality = specialityRepository.save(speciality);
        log.info("Speciality created successfully with id: {}", savedSpeciality.getId());
        return savedSpeciality.getId();
    }

    @Override
    @Transactional
    public void updateSpeciality(Integer id, UpdateSpecialityCommand command) {
        log.info("Updating speciality with id: {}", id);
        
        Speciality existingSpeciality = specialityRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Speciality not found with id: {}", id);
                    return new SpecialityNotFoundException("Speciality not found with id: " + id);
                });

        // Check if another speciality with the same name already exists
        if (!existingSpeciality.getName().equals(command.getName()) &&
                specialityRepository.existsByName(command.getName())) {
            log.warn("Another speciality already exists with name: {}", command.getName());
            throw new SpecialityAlreadyExistsException("Another speciality already exists with name: " + command.getName());
        }

        // Update the existing speciality
        specialityMapper.updateEntityFromCommand(command, existingSpeciality);
        specialityRepository.save(existingSpeciality);
        log.info("Speciality updated successfully with id: {}", existingSpeciality.getId());
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