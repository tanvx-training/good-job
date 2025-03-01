package com.goodjob.candidate.command.service.impl;

import com.goodjob.candidate.command.service.CandidateSkillCommandService;
import com.goodjob.candidate.dto.CandidateSkillCommand;
import com.goodjob.candidate.dto.CandidateSkillResponse;
import com.goodjob.candidate.entity.Candidate;
import com.goodjob.candidate.entity.CandidateSkill;
import com.goodjob.candidate.exception.CandidateNotFoundException;
import com.goodjob.candidate.exception.ResourceNotFoundException;
import com.goodjob.candidate.exception.UnauthorizedAccessException;
import com.goodjob.candidate.repository.CandidateRepository;
import com.goodjob.candidate.repository.CandidateSkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CandidateSkillCommandService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateSkillCommandServiceImpl implements CandidateSkillCommandService {

    private final CandidateRepository candidateRepository;
    private final CandidateSkillRepository skillRepository;

    /**
     * Adds a skill to a candidate.
     *
     * @param userId the user ID
     * @param command the skill command
     * @return the created skill response
     */
    @Override
    public CandidateSkillResponse addSkill(String userId, CandidateSkillCommand command) {
        log.info("Adding skill for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        // Check if skill already exists
        skillRepository.findByCandidateIdAndNameIgnoreCase(candidate.getId().toString(), command.getSkillName())
                .ifPresent(skill -> {
                    throw new IllegalStateException("Skill already exists: " + command.getSkillName());
                });
        
        CandidateSkill skill = CandidateSkill.builder()
                .skillName(command.getSkillName())
                .proficiencyLevel(command.getProficiencyLevel())
                .yearsOfExperience(command.getYearsOfExperience())
                .description(command.getDescription())
                .build();
        
        candidate.addSkill(skill);
        candidateRepository.save(candidate);
        
        log.info("Skill added: {}", skill.getSkillName());
        
        return CandidateSkillResponse.fromEntity(skill);
    }

    /**
     * Updates a skill for a candidate.
     *
     * @param userId the user ID
     * @param skillId the skill ID
     * @param command the skill command
     * @return the updated skill response
     */
    @Override
    public CandidateSkillResponse updateSkill(String userId, String skillId, CandidateSkillCommand command) {
        log.info("Updating skill ID: {} for user ID: {}", skillId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateSkill skill = skillRepository.findByCandidateIdAndId(candidate.getId().toString(), skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with ID: " + skillId));
        
        // Check if new skill name already exists (if name is being changed)
        if (!skill.getSkillName().equalsIgnoreCase(command.getSkillName())) {
            skillRepository.findByCandidateIdAndNameIgnoreCase(candidate.getId().toString(), command.getSkillName())
                    .ifPresent(existingSkill -> {
                        throw new IllegalStateException("Skill already exists: " + command.getSkillName());
                    });
        }
        
        skill.setSkillName(command.getSkillName());
        skill.setProficiencyLevel(command.getProficiencyLevel());
        skill.setYearsOfExperience(command.getYearsOfExperience());
        skill.setDescription(command.getDescription());
        
        skillRepository.save(skill);
        
        log.info("Skill updated: {}", skill.getSkillName());
        
        return CandidateSkillResponse.fromEntity(skill);
    }

    /**
     * Deletes a skill from a candidate.
     *
     * @param userId the user ID
     * @param skillId the skill ID
     */
    @Override
    public void deleteSkill(String userId, String skillId) {
        log.info("Deleting skill ID: {} for user ID: {}", skillId, userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        CandidateSkill skill = skillRepository.findByCandidateIdAndId(candidate.getId().toString(), skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with ID: " + skillId));
        
        candidate.removeSkill(skill);
        candidateRepository.save(candidate);
        
        log.info("Skill deleted: {}", skill.getSkillName());
    }

    /**
     * Adds multiple skills to a candidate.
     *
     * @param userId the user ID
     * @param commands the list of skill commands
     * @return the list of created skill responses
     */
    @Override
    public List<CandidateSkillResponse> addSkills(String userId, List<CandidateSkillCommand> commands) {
        log.info("Adding multiple skills for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        List<CandidateSkill> skills = new ArrayList<>();
        
        for (CandidateSkillCommand command : commands) {
            // Check if skill already exists
            if (skillRepository.findByCandidateIdAndNameIgnoreCase(candidate.getId().toString(), command.getSkillName()).isPresent()) {
                log.warn("Skill already exists, skipping: {}", command.getSkillName());
                continue;
            }
            
            CandidateSkill skill = CandidateSkill.builder()
                    .skillName(command.getSkillName())
                    .proficiencyLevel(command.getProficiencyLevel())
                    .yearsOfExperience(command.getYearsOfExperience())
                    .description(command.getDescription())
                    .build();
            
            candidate.addSkill(skill);
            skills.add(skill);
        }
        
        candidateRepository.save(candidate);
        
        log.info("{} skills added", skills.size());
        
        return skills.stream()
                .map(CandidateSkillResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Deletes all skills from a candidate.
     *
     * @param userId the user ID
     */
    @Override
    public void deleteAllSkills(String userId) {
        log.info("Deleting all skills for user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException(userId));
        
        skillRepository.deleteByCandidateId(candidate.getId().toString());
        
        log.info("All skills deleted for candidate ID: {}", candidate.getId());
    }
} 