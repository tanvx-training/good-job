package com.goodjob.metadata.controller;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.metadata.command.dto.CreateSkillCommand;
import com.goodjob.metadata.command.dto.UpdateSkillCommand;
import com.goodjob.metadata.command.service.SkillCommandService;
import com.goodjob.metadata.query.dto.SkillQuery;
import com.goodjob.metadata.query.dto.SkillView;
import com.goodjob.metadata.query.service.SkillQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing skills. Following the CQRS pattern, this controller separates query
 * and command operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/metadata/skills")
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

  @GetMapping("/batch")
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SKILL')")
  public ResponseEntity<ApiResponse<List<SkillView>>> getBatchSkills(@RequestParam("ids") String ids) {
    List<Integer> idList = Arrays.stream(ids.split(","))
        .map(Integer::parseInt)
        .toList();
    return ResponseEntity.ok(ApiResponse.success(skillQueryService.getAllByIdList(idList)));
  }
} 