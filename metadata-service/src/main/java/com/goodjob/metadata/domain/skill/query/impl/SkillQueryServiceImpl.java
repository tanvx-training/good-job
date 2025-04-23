package com.goodjob.metadata.domain.skill.query.impl;

import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.common.exception.ResourceNotFoundException;
import com.goodjob.metadata.domain.skill.entity.Skill;
import com.goodjob.metadata.domain.skill.dto.SkillQuery;
import com.goodjob.metadata.domain.skill.dto.SkillView;
import com.goodjob.metadata.domain.skill.query.SkillQueryService;
import com.goodjob.metadata.domain.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        .orElseThrow(() -> new ResourceNotFoundException(Skill.class.getName(), "ID", id));
    return this.mapToSkillView(skill);
  }

  @Override
  public SkillView getSkillByAbbreviation(String abbreviation) {

    log.info("Retrieving skill with abbreviation: {}", abbreviation);

    Skill skill = skillRepository.findByAbbreviationAndDeleteFlg(abbreviation, false)
        .orElseThrow(
            () -> new ResourceNotFoundException(Skill.class.getName(), "Abbreviation", abbreviation));

    return this.mapToSkillView(skill);
  }

  @Override
  public List<SkillView> getAllByIdList(List<Integer> idList) {
    List<Skill> skillList = skillRepository.findAllById(idList);
    if (!Objects.equals(idList.size(), skillList.size())) {
      List<Integer> existedIds = skillList
          .stream()
          .map(Skill::getSkillId)
          .toList();
      List<Integer> notExistedIds = idList.stream()
          .filter(id -> !existedIds.contains(id))
          .toList();
      throw new ResourceNotFoundException(Skill.class.getName(), "ID", String.valueOf(idList));
    }
    return skillList
        .stream()
        .map(this::mapToSkillView)
        .toList();
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