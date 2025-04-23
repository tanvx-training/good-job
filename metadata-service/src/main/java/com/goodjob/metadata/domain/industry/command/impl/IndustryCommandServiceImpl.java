package com.goodjob.metadata.domain.industry.command.impl;

import com.goodjob.common.application.exception.ResourceExistedException;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.metadata.domain.industry.dto.CreateIndustryCommand;
import com.goodjob.metadata.domain.industry.dto.UpdateIndustryCommand;
import com.goodjob.metadata.domain.industry.command.IndustryCommandService;
import com.goodjob.metadata.domain.industry.entity.Industry;
import com.goodjob.metadata.domain.industry.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of the IndustryCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IndustryCommandServiceImpl implements IndustryCommandService {

    private final IndustryRepository industryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer createIndustry(CreateIndustryCommand command) {
        log.info("Creating new industry: {}", command.getName());
        
        if (industryRepository.existsByIndustryName(command.getName())) {
            throw new ResourceExistedException(Industry.class.getName(), "Name", command.getName());
        }
        
        Industry industry = toEntityForCreate(command);
        industryRepository.save(industry);
        log.info("Industry created successfully with ID: {}", industry.getIndustryId());
        
        return industry.getIndustryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateIndustry(Integer id, UpdateIndustryCommand command) {
        log.info("Updating industry with ID: {}", id);

        Industry existingIndustry = industryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Industry.class.getName(), "ID", id));
        
        // Check if name is being changed and if the new name already exists
        if (!existingIndustry.getIndustryName().equals(command.getName()) &&
                industryRepository.existsByIndustryName(command.getName())) {
            throw new ResourceExistedException(Industry.class.getName(), "Name", command.getName());
        }
        
        mergeEntityForUpdate(existingIndustry, command);
        
        industryRepository.save(existingIndustry);
        log.info("Industry updated successfully with ID: {}", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteIndustry(Integer id) {
        log.info("Deleting industry with ID: {}", id);
        
        if (!industryRepository.existsById(id)) {
            throw new ResourceNotFoundException(Industry.class.getName(), "ID", id);
        }
        
        industryRepository.deleteById(id);
        log.info("Industry deleted successfully with ID: {}", id);
    }

    private Industry toEntityForCreate (CreateIndustryCommand command) {
        return Industry.builder()
                .industryName(command.getName())
                .createdOn(LocalDateTime.now())
                .deleteFlg(false)
                .build();
    }

    private void mergeEntityForUpdate (Industry origin, UpdateIndustryCommand command) {
        origin.setIndustryName(command.getName());
        origin.setLastModifiedOn(LocalDateTime.now());
    }
} 