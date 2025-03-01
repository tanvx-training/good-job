package com.goodjob.skill.command.service.impl;

import com.goodjob.skill.command.dto.CreateSkillCommand;
import com.goodjob.skill.command.dto.UpdateSkillCommand;
import com.goodjob.skill.command.service.SkillCommandService;
import com.goodjob.skill.entity.Skill;
import com.goodjob.skill.exception.SkillAlreadyExistsException;
import com.goodjob.skill.exception.SkillNotFoundException;
import com.goodjob.skill.mapper.SkillMapper;
import com.goodjob.skill.repository.SkillRepository;
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
    public Integer createSkill(CreateSkillCommand command) {
        log.info("Creating new skill with abbreviation: {}", command.getAbbreviation());
        
        // Check if skill with the same abbreviation already exists
        if (skillRepository.existsByAbbreviation(command.getAbbreviation())) {
            log.warn("Skill already exists with abbreviation: {}", command.getAbbreviation());
            throw new SkillAlreadyExistsException("Skill already exists with abbreviation: " + command.getAbbreviation());
        }

        // Check if skill with the same name already exists
        if (skillRepository.existsByName(command.getName())) {
            log.warn("Skill already exists with name: {}", command.getName());
            throw new SkillAlreadyExistsException("Skill already exists with name: " + command.getName());
        }

        Skill skill = skillMapper.toEntity(command);
        Skill savedSkill = skillRepository.save(skill);
        log.info("Skill created successfully with id: {}", savedSkill.getId());
        return savedSkill.getId();
    }

    @Override
    @Transactional
    public void updateSkill(Integer id, UpdateSkillCommand command) {
        log.info("Updating skill with id: {}", id);
        
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Skill not found with id: {}", id);
                    return new SkillNotFoundException("Skill not found with id: " + id);
                });

        // Check if another skill with the same abbreviation already exists
        if (!existingSkill.getAbbreviation().equals(command.getAbbreviation()) &&
                skillRepository.existsByAbbreviation(command.getAbbreviation())) {
            log.warn("Another skill already exists with abbreviation: {}", command.getAbbreviation());
            throw new SkillAlreadyExistsException("Another skill already exists with abbreviation: " + command.getAbbreviation());
        }

        // Check if another skill with the same name already exists
        if (!existingSkill.getName().equals(command.getName()) &&
                skillRepository.existsByName(command.getName())) {
            log.warn("Another skill already exists with name: {}", command.getName());
            throw new SkillAlreadyExistsException("Another skill already exists with name: " + command.getName());
        }

        // Update the existing skill
        skillMapper.updateEntityFromCommand(command, existingSkill);
        skillRepository.save(existingSkill);
        log.info("Skill updated successfully with id: {}", existingSkill.getId());
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