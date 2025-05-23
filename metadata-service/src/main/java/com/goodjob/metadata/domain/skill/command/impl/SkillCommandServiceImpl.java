package com.goodjob.metadata.domain.skill.command.impl;

import com.goodjob.common.application.exception.ResourceExistedException;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.metadata.domain.skill.dto.CreateSkillCommand;
import com.goodjob.metadata.domain.skill.dto.UpdateSkillCommand;
import com.goodjob.metadata.domain.skill.command.SkillCommandService;
import com.goodjob.metadata.domain.skill.entity.Skill;
import com.goodjob.metadata.domain.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of the SkillCommandService interface. Following the CQRS pattern, this service
 * handles all write operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SkillCommandServiceImpl implements SkillCommandService {

  private final SkillRepository skillRepository;

  @Override
  @Transactional
  public Integer createSkill(CreateSkillCommand command) {
    log.info("Creating new skill with abbreviation: {}", command.getAbbreviation());

    // Check if skill with the same abbreviation already exists
    if (skillRepository.existsByAbbreviationAndDeleteFlg(command.getAbbreviation(), false)) {
      log.warn("Skill already exists with abbreviation: {}", command.getAbbreviation());
      throw new ResourceExistedException(Skill.class.getName(),
          "Abbreviation", command.getAbbreviation());
    }

    // Check if skill with the same name already exists
    if (skillRepository.existsByNameAndDeleteFlg(command.getName(), false)) {
      log.warn("Skill already exists with name: {}", command.getName());
      throw new ResourceExistedException(Skill.class.getName(),
              "name", command.getName());
    }

    Skill skill = this.toEntityForCreate(command);
    skillRepository.save(skill);

    return skill.getSkillId();
  }

  @Override
  @Transactional
  public void updateSkill(Integer id, UpdateSkillCommand command) {
    log.info("Updating skill with id: {}", id);

    Skill existingSkill = skillRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(Skill.class.getName(), "ID", id));

    // Check if another skill with the same abbreviation already exists
    if (!existingSkill.getAbbreviation().equals(command.getAbbreviation()) &&
        skillRepository.existsByAbbreviationAndDeleteFlg(command.getAbbreviation(), false)) {
      log.warn("Another skill already exists with abbreviation: {}", command.getAbbreviation());
      throw new ResourceExistedException(Skill.class.getName(),
          "Abbreviation", command.getAbbreviation());
    }

    // Check if another skill with the same name already exists
    if (!existingSkill.getName().equals(command.getName()) &&
        skillRepository.existsByNameAndDeleteFlg(command.getName(), false)) {
      log.warn("Another skill already exists with name: {}", command.getName());
      throw new ResourceExistedException(Skill.class.getName(),
              "Name", command.getName());
    }

    // Update the existing skill
    this.mergeEntityForUpdate(existingSkill, command);
    skillRepository.save(existingSkill);
  }

  @Override
  @Transactional
  public void deleteSkill(Integer id) {
    log.info("Deleting skill with id: {}", id);

    if (!skillRepository.existsById(id)) {
      throw new ResourceNotFoundException(Skill.class.getName(), "ID", id);
    }

    skillRepository.deleteById(id);
  }

  private Skill toEntityForCreate(CreateSkillCommand command) {
    return Skill.builder()
        .abbreviation(command.getAbbreviation())
        .name(command.getName())
        .createdOn(LocalDateTime.now())
        .deleteFlg(false)
        .build();
  }

  private void mergeEntityForUpdate(Skill origin, UpdateSkillCommand command) {
    origin.setAbbreviation(command.getAbbreviation());
    origin.setName(command.getName());
    origin.setLastModifiedOn(LocalDateTime.now());
  }
} 