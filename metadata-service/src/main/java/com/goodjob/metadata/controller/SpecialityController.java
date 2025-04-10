package com.goodjob.metadata.controller;

import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.dto.response.PageResponseDTO;
import com.goodjob.metadata.command.dto.CreateSpecialityCommand;
import com.goodjob.metadata.command.dto.UpdateSpecialityCommand;
import com.goodjob.metadata.command.service.SpecialityCommandService;
import com.goodjob.metadata.query.dto.SpecialityQuery;
import com.goodjob.metadata.query.dto.SpecialityView;
import com.goodjob.metadata.query.service.SpecialityQueryService;
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
 * REST controller for managing specialities.
 * Following the CQRS pattern, this controller separates query and command operations.
 */
@RestController
@RequestMapping("/api/v1/metadata/specialities")
@RequiredArgsConstructor
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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SPECIALITY')")
    public ResponseEntity<ApiResponse<PageResponseDTO<SpecialityView>>> getAllSpecialities(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "specialityId,asc") String sort) {
        log.info("REST request to get all Specialities");
        return ResponseEntity.ok(ApiResponse.success(specialityQueryService.getAllSpecialities(
                SpecialityQuery.builder()
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build()
        )));
    }

    /**
     * GET /specialities/{id} : Get a speciality by ID.
     *
     * @param id the ID of the speciality to retrieve
     * @return the ResponseEntity with status 200 (OK) and the speciality in the body
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SPECIALITY')")
    public ResponseEntity<ApiResponse<SpecialityView>> getSpecialityById(@PathVariable Integer id) {
        log.info("REST request to get Speciality : {}", id);
        SpecialityView speciality = specialityQueryService.getSpecialityById(id);
        return ResponseEntity.ok(ApiResponse.success(speciality));
    }

    /**
     * GET /specialities/name/{name} : Get a speciality by name.
     *
     * @param name the name of the speciality to retrieve
     * @return the ResponseEntity with status 200 (OK) and the speciality in the body
     */
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SPECIALITY')")
    public ResponseEntity<ApiResponse<SpecialityView>> getSpecialityByName(@PathVariable String name) {
        log.info("REST request to get Speciality by name : {}", name);
        SpecialityView speciality = specialityQueryService.getSpecialityByName(name);
        return ResponseEntity.ok(ApiResponse.success(speciality));
    }

    // Command operations (Create, Update, Delete)

    /**
     * POST /specialities : Create a new speciality.
     *
     * @param command the create speciality command
     * @return the ResponseEntity with status 201 (Created) and URI to the new speciality
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE_SPECIALITY')")
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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('UPDATE_SPECIALITY')")
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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('DELTE_SPECIALITY')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSpeciality(@PathVariable Integer id) {
        log.info("REST request to delete Speciality : {}", id);
        specialityCommandService.deleteSpeciality(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/batch")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_SPECIALITY')")
    public ResponseEntity<ApiResponse<List<SpecialityView>>> getBatchSpeciality(@RequestParam("ids") String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(specialityQueryService.getAllByIdList(idList)));
    }
} 