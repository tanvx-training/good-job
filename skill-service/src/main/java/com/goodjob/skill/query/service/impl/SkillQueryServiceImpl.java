package com.goodjob.skill.query.service.impl;

import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.skill.entity.Skill;
import com.goodjob.skill.exception.SkillNotFoundException;
import com.goodjob.skill.query.dto.SkillQuery;
import com.goodjob.skill.query.dto.SkillView;
import com.goodjob.skill.query.service.SkillQueryService;
import com.goodjob.skill.repository.SkillRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the SkillQueryService interface. Following the CQRS pattern, this service
 * handles all read operations.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SkillQueryServiceImpl implements SkillQueryService {

  private final SkillRepository skillRepository;

  @Override
  public PageResponseDTO<SkillView> getAllSkills(SkillQuery query) {

    log.info("Fetching all skills");

    String[] parts = query.getSort().split(",");
    Sort sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
    Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

    return new PageResponseDTO<>(skillRepository.findAllByDeleteFlg(false, pageable)
        .map(this::mapToSkillView));
  }

  @Override
  public SkillView getSkillById(Integer id) {

    log.info("Retrieving skill with id: {}", id);

    Skill skill = skillRepository.findById(id)
        .filter(s -> !s.isDeleteFlg())
        .orElseThrow(() -> new SkillNotFoundException("Skill not found with ID: " + id));
    return this.mapToSkillView(skill);
  }

  @Override
  public SkillView getSkillByAbbreviation(String abbreviation) {

    log.info("Retrieving skill with abbreviation: {}", abbreviation);

    Skill skill = skillRepository.findByAbbreviationAndDeleteFlg(abbreviation, false)
        .orElseThrow(
            () -> new SkillNotFoundException("Skill not found with abbreviation: " + abbreviation));

    return this.mapToSkillView(skill);
  }

  private SkillView mapToSkillView(Skill skill) {

    return SkillView.builder()
        .id(skill.getSkillId())
        .abbreviation(skill.getAbbreviation())
        .name(skill.getName())
        .createdOn(skill.getCreatedOn())
        .createdBy(skill.getCreatedBy())
        .lastModifiedOn(skill.getLastModifiedOn())
        .lastModifiedBy(skill.getLastModifiedBy())
        .deleteFlg(skill.isDeleteFlg())
        .build();
  }
} 