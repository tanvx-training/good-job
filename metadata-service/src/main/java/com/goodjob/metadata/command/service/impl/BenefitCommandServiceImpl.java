package com.goodjob.metadata.command.service.impl;

import com.goodjob.metadata.command.dto.CreateBenefitCommand;
import com.goodjob.metadata.command.dto.UpdateBenefitCommand;
import com.goodjob.metadata.command.service.BenefitCommandService;
import com.goodjob.metadata.entity.Benefit;
import com.goodjob.metadata.exception.BenefitAlreadyExistsException;
import com.goodjob.metadata.exception.BenefitNotFoundException;
import com.goodjob.metadata.repository.BenefitRepository;
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
            throw new BenefitAlreadyExistsException("Benefit with type " + command.getType() + " already exists");
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
                .orElseThrow(() -> new BenefitNotFoundException("Benefit not found with ID: " + id));
        
        // Check if type is being changed and if the new type already exists
        if (!existingBenefit.getType().equals(command.getType()) && 
                benefitRepository.existsByType(command.getType())) {
            throw new BenefitAlreadyExistsException("Benefit with type " + command.getType() + " already exists");
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
            throw new BenefitNotFoundException("Benefit not found with ID: " + id);
        }
        
        benefitRepository.deleteById(id);
        log.info("Benefit deleted successfully with ID: {}", id);
    }
} 