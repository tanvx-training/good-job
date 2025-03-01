package com.goodjob.speciality.controller;

import com.goodjob.speciality.command.dto.CreateSpecialityCommand;
import com.goodjob.speciality.command.dto.UpdateSpecialityCommand;
import com.goodjob.speciality.command.service.SpecialityCommandService;
import com.goodjob.speciality.query.dto.SpecialityView;
import com.goodjob.speciality.query.service.SpecialityQueryService;
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
 * REST controller for managing specialities.
 * Following the CQRS pattern, this controller separates query and command operations.
 */
@RestController
@RequestMapping("/specialities")
@RequiredArgsConstructor
@Tag(name = "Specialities", description = "Speciality Management API")
@Slf4j
public class SpecialityController {

    private final SpecialityQueryService specialityQueryService;
    private final SpecialityCommandService specialityCommandService;

    // Query operations (Read)

    /**
     * GET /specialities : Get all specialities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialities in the body
     */
    @GetMapping
    @Operation(
        summary = "Get all specialities",
        description = "Returns a list of all specialities"
    )
    public ResponseEntity<List<SpecialityView>> getAllSpecialities() {
        log.info("REST request to get all Specialities");
        List<SpecialityView> specialities = specialityQueryService.getAllSpecialities();
        return ResponseEntity.ok(specialities);
    }

    /**
     * GET /specialities/{id} : Get a speciality by ID.
     *
     * @param id the ID of the speciality to retrieve
     * @return the ResponseEntity with status 200 (OK) and the speciality in the body
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get speciality by ID",
        description = "Returns a speciality by its ID"
    )
    public ResponseEntity<SpecialityView> getSpecialityById(@PathVariable Integer id) {
        log.info("REST request to get Speciality : {}", id);
        SpecialityView speciality = specialityQueryService.getSpecialityById(id);
        return ResponseEntity.ok(speciality);
    }

    /**
     * GET /specialities/name/{name} : Get a speciality by name.
     *
     * @param name the name of the speciality to retrieve
     * @return the ResponseEntity with status 200 (OK) and the speciality in the body
     */
    @GetMapping("/name/{name}")
    @Operation(
        summary = "Get speciality by name",
        description = "Returns a speciality by its name"
    )
    public ResponseEntity<SpecialityView> getSpecialityByName(@PathVariable String name) {
        log.info("REST request to get Speciality by name : {}", name);
        SpecialityView speciality = specialityQueryService.getSpecialityByName(name);
        return ResponseEntity.ok(speciality);
    }

    // Command operations (Create, Update, Delete)

    /**
     * POST /specialities : Create a new speciality.
     *
     * @param command the create speciality command
     * @return the ResponseEntity with status 201 (Created) and URI to the new speciality
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Create a new speciality",
        description = "Creates a new speciality (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> createSpeciality(@Valid @RequestBody CreateSpecialityCommand command) {
        log.info("REST request to create Speciality : {}", command);
        Integer specialityId = specialityCommandService.createSpeciality(command);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(specialityId)
                .toUri();
        
        return ResponseEntity.created(location).build();
    }

    /**
     * PUT /specialities/{id} : Update an existing speciality.
     *
     * @param id the ID of the speciality to update
     * @param command the update speciality command
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update speciality",
        description = "Updates an existing speciality (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> updateSpeciality(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateSpecialityCommand command) {
        log.info("REST request to update Speciality : {}, {}", id, command);
        specialityCommandService.updateSpeciality(id, command);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /specialities/{id} : Delete a speciality.
     *
     * @param id the ID of the speciality to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete speciality",
        description = "Deletes a speciality by its ID (Admin only)",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<Void> deleteSpeciality(@PathVariable Integer id) {
        log.info("REST request to delete Speciality : {}", id);
        specialityCommandService.deleteSpeciality(id);
        return ResponseEntity.noContent().build();
    }
} 