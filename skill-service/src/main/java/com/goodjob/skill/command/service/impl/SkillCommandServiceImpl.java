package com.goodjob.skill.command.service.impl;

import com.goodjob.skill.command.dto.CreateSkillCommand;
import com.goodjob.skill.command.dto.UpdateSkillCommand;
import com.goodjob.skill.command.service.SkillCommandService;
import com.goodjob.skill.entity.Skill;
import com.goodjob.skill.exception.SkillAlreadyExistsException;
import com.goodjob.skill.exception.SkillNotFoundException;
import com.goodjob.skill.repository.SkillRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      throw new SkillAlreadyExistsException(
          "Skill already exists with abbreviation: " + command.getAbbreviation());
    }

    // Check if skill with the same name already exists
    if (skillRepository.existsByNameAndDeleteFlg(command.getName(), false)) {
      log.warn("Skill already exists with name: {}", command.getName());
      throw new SkillAlreadyExistsException("Skill already exists with name: " + command.getName());
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
        .orElseThrow(() -> new SkillNotFoundException("Skill not found with ID: " + id));

    // Check if another skill with the same abbreviation already exists
    if (!existingSkill.getAbbreviation().equals(command.getAbbreviation()) &&
        skillRepository.existsByAbbreviationAndDeleteFlg(command.getAbbreviation(), false)) {
      log.warn("Another skill already exists with abbreviation: {}", command.getAbbreviation());
      throw new SkillAlreadyExistsException(
          "Another skill already exists with abbreviation: " + command.getAbbreviation());
    }

    // Check if another skill with the same name already exists
    if (!existingSkill.getName().equals(command.getName()) &&
        skillRepository.existsByNameAndDeleteFlg(command.getName(), false)) {
      log.warn("Another skill already exists with name: {}", command.getName());
      throw new SkillAlreadyExistsException(
          "Another skill already exists with name: " + command.getName());
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
      throw new SkillNotFoundException("Skill not found with ID: " + id);
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