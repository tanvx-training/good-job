package com.goodjob.skill.service.impl;

import com.goodjob.skill.dto.SkillDto;
import com.goodjob.skill.entity.Skill;
import com.goodjob.skill.exception.SkillNotFoundException;
import com.goodjob.skill.mapper.SkillMapper;
import com.goodjob.skill.repository.SkillRepository;
import com.goodjob.skill.service.SkillQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the SkillQueryService interface.
 * Following the CQRS pattern, this service handles all read operations.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SkillQueryServiceImpl implements SkillQueryService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    public List<SkillDto> getAllSkills() {
        log.info("Retrieving all skills");
        List<Skill> skills = skillRepository.findAll();
        log.info("Found {} skills", skills.size());
        return skillMapper.toDtoList(skills);
    }

    @Override
    public SkillDto getSkillById(Integer id) {
        log.info("Retrieving skill with id: {}", id);
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Skill not found with id: {}", id);
                    return new SkillNotFoundException("Skill not found with id: " + id);
                });
        log.info("Found skill with id: {}", id);
        return skillMapper.toDto(skill);
    }

    @Override
    public SkillDto getSkillByAbbreviation(String abbreviation) {
        log.info("Retrieving skill with abbreviation: {}", abbreviation);
        Skill skill = skillRepository.findByAbbreviation(abbreviation)
                .orElseThrow(() -> {
                    log.warn("Skill not found with abbreviation: {}", abbreviation);
                    return new SkillNotFoundException("Skill not found with abbreviation: " + abbreviation);
                });
        log.info("Found skill with abbreviation: {}", abbreviation);
        return skillMapper.toDto(skill);
    }
} 