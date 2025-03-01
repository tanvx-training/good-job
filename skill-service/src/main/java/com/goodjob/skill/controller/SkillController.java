package com.goodjob.skill.controller;

import com.goodjob.skill.command.dto.CreateSkillCommand;
import com.goodjob.skill.command.dto.UpdateSkillCommand;
import com.goodjob.skill.command.service.SkillCommandService;
import com.goodjob.skill.query.dto.SkillView;
import com.goodjob.skill.query.service.SkillQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing skills.
 * Following the CQRS pattern, this controller separates query and command operations.
 */
@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
@Tag(name = "Skills", description = "Skill Management API")
@Slf4j
public class SkillController {

    private final SkillQueryService skillQueryService;
    private final SkillCommandService skillCommandService;

    /**
     * POST /skills : Create a new skill.
     *
     * @param command the create skill command
     * @return the ResponseEntity with status 201 (Created) and URI to the new skill
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Create a new skill",
        description = "Creates a new skill (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
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

    /**
     * GET /skills : Get all skills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skills in the body
     */
    @GetMapping
    @Operation(
        summary = "Get all skills",
        description = "Returns a list of all skills"
    )
    public ResponseEntity<List<SkillView>> getAllSkills() {
        log.info("REST request to get all Skills");
        List<SkillView> skills = skillQueryService.getAllSkills();
        return ResponseEntity.ok(skills);
    }

    /**
     * GET /skills/{id} : Get a skill by ID.
     *
     * @param id the ID of the skill to retrieve
     * @return the ResponseEntity with status 200 (OK) and the skill in the body
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get skill by ID",
        description = "Returns a skill by its ID"
    )
    public ResponseEntity<SkillView> getSkillById(@PathVariable Integer id) {
        log.info("REST request to get Skill : {}", id);
        SkillView skill = skillQueryService.getSkillById(id);
        return ResponseEntity.ok(skill);
    }

    /**
     * GET /skills/abbreviation/{abbreviation} : Get a skill by abbreviation.
     *
     * @param abbreviation the abbreviation of the skill to retrieve
     * @return the ResponseEntity with status 200 (OK) and the skill in the body
     */
    @GetMapping("/abbreviation/{abbreviation}")
    @Operation(
        summary = "Get skill by abbreviation",
        description = "Returns a skill by its abbreviation"
    )
    public ResponseEntity<SkillView> getSkillByAbbreviation(@PathVariable String abbreviation) {
        log.info("REST request to get Skill by abbreviation : {}", abbreviation);
        SkillView skill = skillQueryService.getSkillByAbbreviation(abbreviation);
        return ResponseEntity.ok(skill);
    }

    /**
     * PUT /skills/{id} : Update an existing skill.
     *
     * @param id the ID of the skill to update
     * @param command the update skill command
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update skill",
        description = "Updates an existing skill (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> updateSkill(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateSkillCommand command) {
        log.info("REST request to update Skill : {}, {}", id, command);
        skillCommandService.updateSkill(id, command);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /skills/{id} : Delete a skill.
     *
     * @param id the ID of the skill to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete skill",
        description = "Deletes a skill by its ID (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteSkill(@PathVariable Integer id) {
        log.info("REST request to delete Skill : {}", id);
        skillCommandService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
} 