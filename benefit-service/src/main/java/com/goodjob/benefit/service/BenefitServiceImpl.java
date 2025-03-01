package com.goodjob.benefit.service;

import com.goodjob.benefit.dto.BenefitDto;
import com.goodjob.benefit.entity.Benefit;
import com.goodjob.benefit.exception.BenefitAlreadyExistsException;
import com.goodjob.benefit.exception.BenefitNotFoundException;
import com.goodjob.benefit.mapper.BenefitMapper;
import com.goodjob.benefit.repository.BenefitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the BenefitService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;
    private final BenefitMapper benefitMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BenefitDto createBenefit(BenefitDto benefitDto) {
        log.info("Creating new benefit: {}", benefitDto.getName());
        
        if (benefitRepository.existsByName(benefitDto.getName())) {
            throw new BenefitAlreadyExistsException("Benefit with name " + benefitDto.getName() + " already exists");
        }
        
        Benefit benefit = benefitMapper.toEntity(benefitDto);
        benefit.setActive(true);
        Benefit savedBenefit = benefitRepository.save(benefit);
        
        log.info("Benefit created successfully with ID: {}", savedBenefit.getId());
        return benefitMapper.toDto(savedBenefit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<BenefitDto> getAllBenefits() {
        log.info("Fetching all benefits");
        List<Benefit> benefits = benefitRepository.findAll();
        return benefitMapper.toDtoList(benefits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<BenefitDto> getActiveBenefits() {
        log.info("Fetching all active benefits");
        List<Benefit> activeBenefits = benefitRepository.findByActiveTrue();
        return benefitMapper.toDtoList(activeBenefits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public BenefitDto getBenefitById(Long id) {
        log.info("Fetching benefit with ID: {}", id);
        Benefit benefit = benefitRepository.findById(id)
                .orElseThrow(() -> new BenefitNotFoundException("Benefit not found with ID: " + id));
        return benefitMapper.toDto(benefit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BenefitDto updateBenefit(Long id, BenefitDto benefitDto) {
        log.info("Updating benefit with ID: {}", id);
        
        Benefit existingBenefit = benefitRepository.findById(id)
                .orElseThrow(() -> new BenefitNotFoundException("Benefit not found with ID: " + id));
        
        // Check if name is being changed and if the new name already exists
        if (!existingBenefit.getName().equals(benefitDto.getName()) && 
                benefitRepository.existsByName(benefitDto.getName())) {
            throw new BenefitAlreadyExistsException("Benefit with name " + benefitDto.getName() + " already exists");
        }
        
        benefitMapper.updateBenefitFromDto(benefitDto, existingBenefit);
        Benefit updatedBenefit = benefitRepository.save(existingBenefit);
        
        log.info("Benefit updated successfully with ID: {}", updatedBenefit.getId());
        return benefitMapper.toDto(updatedBenefit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteBenefit(Long id) {
        log.info("Deleting benefit with ID: {}", id);
        
        if (!benefitRepository.existsById(id)) {
            throw new BenefitNotFoundException("Benefit not found with ID: " + id);
        }
        
        benefitRepository.deleteById(id);
        log.info("Benefit deleted successfully with ID: {}", id);
    }
} 