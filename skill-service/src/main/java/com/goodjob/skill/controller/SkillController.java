package com.goodjob.skill.controller;

import com.goodjob.common.dto.ApiResponse;
import com.goodjob.common.dto.PageResponseDTO;
import com.goodjob.skill.command.dto.CreateSkillCommand;
import com.goodjob.skill.command.dto.UpdateSkillCommand;
import com.goodjob.skill.command.service.SkillCommandService;
import com.goodjob.skill.query.dto.SkillQuery;
import com.goodjob.skill.query.dto.SkillView;
import com.goodjob.skill.query.service.SkillQueryService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing skills. Following the CQRS pattern, this controller separates query
 * and command operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

  private final SkillQueryService skillQueryService;

  private final SkillCommandService skillCommandService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE_SKILL')")
  public ResponseEntity<Void> createSkill(@Valid @RequestBody CreateSkillCommand command) {
    log.info("REST request to create Skill : {}", command);
    Integer skillId = skillCommandService.createSkill(command);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(skillId)
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SKILL')")
  public ResponseEntity<ApiResponse<PageResponseDTO<SkillView>>> getAllSkills(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "20") Integer size,
      @RequestParam(value = "sort", defaultValue = "skillId,asc") String sort) {
    log.info("REST request to get all Skills");
    PageResponseDTO<SkillView> skills = skillQueryService.getAllSkills(SkillQuery.builder()
        .page(page)
        .size(size)
        .sort(sort)
        .build());
    return ResponseEntity.ok(ApiResponse.success(skills));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SKILL')")
  public ResponseEntity<ApiResponse<SkillView>> getSkillById(@PathVariable Integer id) {
    log.info("REST request to get Skill : {}", id);
    SkillView skill = skillQueryService.getSkillById(id);
    return ResponseEntity.ok(ApiResponse.success(skill));
  }

  @GetMapping("/abbreviation/{abbreviation}")
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SKILL')")
  public ResponseEntity<ApiResponse<SkillView>> getSkillByAbbreviation(@PathVariable String abbreviation) {
    log.info("REST request to get Skill by abbreviation : {}", abbreviation);
    SkillView skill = skillQueryService.getSkillByAbbreviation(abbreviation);
    return ResponseEntity.ok(ApiResponse.success(skill));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('UPDATE_SKILL')")
  public ResponseEntity<Void> updateSkill(
      @PathVariable Integer id,
      @Valid @RequestBody UpdateSkillCommand command) {
    log.info("REST request to update Skill : {}, {}", id, command);
    skillCommandService.updateSkill(id, command);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('DELETE_SKILL')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deleteSkill(@PathVariable Integer id) {
    log.info("REST request to delete Skill : {}", id);
    skillCommandService.deleteSkill(id);
    return ResponseEntity.noContent().build();
  }
} 