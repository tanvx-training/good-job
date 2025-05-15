package com.goodjob.profile.api.rest;

import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.profile.domain.profile.dto.ProfileDTO;
import com.goodjob.profile.domain.profile.dto.ProfileSearchQuery;
import com.goodjob.profile.domain.profile.query.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing profiles.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileQueryService profileQueryService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('READ_PROFILE')")
    public ResponseEntity<ApiResponse<PageResponseDTO<ProfileDTO>>> searchProfiles(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "profileId,asc") String sort,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        ProfileSearchQuery query = ProfileSearchQuery.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .keyword(keyword)
                .build();

        return ResponseEntity.ok(ApiResponse.success(profileQueryService.searchProfiles(query)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER') and hasAuthority('READ_PROFILE')")
    public ResponseEntity<ApiResponse<ProfileDTO>> getProfileById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(profileQueryService.getProfileById(id)));
    }
} 