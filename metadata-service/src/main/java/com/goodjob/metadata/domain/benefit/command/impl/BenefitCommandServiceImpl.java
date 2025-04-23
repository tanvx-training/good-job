package com.goodjob.metadata.domain.benefit.command.impl;

import com.goodjob.common.application.exception.ResourceExistedException;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.metadata.domain.benefit.command.BenefitCommandService;
import com.goodjob.metadata.domain.benefit.dto.CreateBenefitCommand;
import com.goodjob.metadata.domain.benefit.dto.UpdateBenefitCommand;
import com.goodjob.metadata.domain.benefit.entity.Benefit;
import com.goodjob.metadata.domain.benefit.repository.BenefitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the BenefitCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BenefitCommandServiceImpl implements BenefitCommandService {

    private final BenefitRepository benefitRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer createBenefit(CreateBenefitCommand command) {
        log.info("Creating new benefit: {}", command.getType());
        
        if (benefitRepository.existsByType(command.getType())) {
            throw new ResourceExistedException(Benefit.class.getName(), "Type", command.getType());
        }
        
        Benefit benefit = Benefit.builder()
                .type(command.getType())
                .build();
        
        Benefit savedBenefit = benefitRepository.save(benefit);
        log.info("Benefit created successfully with ID: {}", savedBenefit.getBenefitId());
        
        return savedBenefit.getBenefitId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateBenefit(Integer id, UpdateBenefitCommand command) {
        log.info("Updating benefit with ID: {}", id);
        
        Benefit existingBenefit = benefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Benefit.class.getName(), "ID", id));
        
        // Check if type is being changed and if the new type already exists
        if (!existingBenefit.getType().equals(command.getType()) && 
                benefitRepository.existsByType(command.getType())) {
            throw new ResourceExistedException(Benefit.class.getName(), "Type", command.getType());
        }
        
        existingBenefit.setType(command.getType());
        
        benefitRepository.save(existingBenefit);
        log.info("Benefit updated successfully with ID: {}", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteBenefit(Integer id) {
        log.info("Deleting benefit with ID: {}", id);
        
        if (!benefitRepository.existsById(id)) {
            throw new ResourceNotFoundException(Benefit.class.getName(), "ID", id);
        }
        
        benefitRepository.deleteById(id);
        log.info("Benefit deleted successfully with ID: {}", id);
    }
} 