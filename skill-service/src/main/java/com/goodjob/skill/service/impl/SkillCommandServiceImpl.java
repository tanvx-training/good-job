package com.goodjob.skill.service.impl;

import com.goodjob.skill.dto.SkillDto;
import com.goodjob.skill.entity.Skill;
import com.goodjob.skill.exception.SkillAlreadyExistsException;
import com.goodjob.skill.exception.SkillNotFoundException;
import com.goodjob.skill.mapper.SkillMapper;
import com.goodjob.skill.repository.SkillRepository;
import com.goodjob.skill.service.SkillCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the SkillCommandService interface.
 * Following the CQRS pattern, this service handles all write operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SkillCommandServiceImpl implements SkillCommandService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    @Transactional
    public SkillDto createSkill(SkillDto skillDto) {
        log.info("Creating new skill with abbreviation: {}", skillDto.getAbbreviation());
        
        // Check if skill with the same abbreviation already exists
        if (skillRepository.existsByAbbreviation(skillDto.getAbbreviation())) {
            log.warn("Skill already exists with abbreviation: {}", skillDto.getAbbreviation());
            throw new SkillAlreadyExistsException("Skill already exists with abbreviation: " + skillDto.getAbbreviation());
        }

        // Check if skill with the same name already exists
        if (skillRepository.existsByName(skillDto.getName())) {
            log.warn("Skill already exists with name: {}", skillDto.getName());
            throw new SkillAlreadyExistsException("Skill already exists with name: " + skillDto.getName());
        }

        Skill skill = skillMapper.toEntity(skillDto);
        Skill savedSkill = skillRepository.save(skill);
        log.info("Skill created successfully with id: {}", savedSkill.getId());
        return skillMapper.toDto(savedSkill);
    }

    @Override
    @Transactional
    public SkillDto updateSkill(Integer id, SkillDto skillDto) {
        log.info("Updating skill with id: {}", id);
        
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Skill not found with id: {}", id);
                    return new SkillNotFoundException("Skill not found with id: " + id);
                });

        // Check if another skill with the same abbreviation already exists
        if (!existingSkill.getAbbreviation().equals(skillDto.getAbbreviation()) &&
                skillRepository.existsByAbbreviation(skillDto.getAbbreviation())) {
            log.warn("Another skill already exists with abbreviation: {}", skillDto.getAbbreviation());
            throw new SkillAlreadyExistsException("Another skill already exists with abbreviation: " + skillDto.getAbbreviation());
        }

        // Check if another skill with the same name already exists
        if (!existingSkill.getName().equals(skillDto.getName()) &&
                skillRepository.existsByName(skillDto.getName())) {
            log.warn("Another skill already exists with name: {}", skillDto.getName());
            throw new SkillAlreadyExistsException("Another skill already exists with name: " + skillDto.getName());
        }

        // Update the existing skill
        existingSkill.setAbbreviation(skillDto.getAbbreviation());
        existingSkill.setName(skillDto.getName());

        Skill updatedSkill = skillRepository.save(existingSkill);
        log.info("Skill updated successfully with id: {}", updatedSkill.getId());
        return skillMapper.toDto(updatedSkill);
    }

    @Override
    @Transactional
    public void deleteSkill(Integer id) {
        log.info("Deleting skill with id: {}", id);
        
        if (!skillRepository.existsById(id)) {
            log.warn("Skill not found with id: {}", id);
            throw new SkillNotFoundException("Skill not found with id: " + id);
        }
        
        skillRepository.deleteById(id);
        log.info("Skill deleted successfully with id: {}", id);
    }
} 