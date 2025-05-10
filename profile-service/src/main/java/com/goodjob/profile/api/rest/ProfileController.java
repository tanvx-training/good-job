package com.goodjob.profile.api.rest;

import com.goodjob.common.application.dto.response.ApiResponse;
import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.profile.domain.profile.command.ProfileCommandService;
import com.goodjob.profile.domain.profile.dto.ProfileDTO;
import com.goodjob.profile.domain.profile.dto.ProfileSearchQuery;
import com.goodjob.profile.domain.profile.query.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private final ProfileCommandService profileCommandService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_PROFILE')")
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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') and hasAuthority('READ_PROFILE')")
    public ResponseEntity<ApiResponse<ProfileDTO>> getProfileById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ApiResponse.success(profileQueryService.getProfileById(id)));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('READ_PROFILE')")
    public ResponseEntity<ApiResponse<ProfileDTO>> getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return ResponseEntity.ok(ApiResponse.success(profileQueryService.getProfileByUserId(userId)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('CREATE_PROFILE')")
    public ResponseEntity<ApiResponse<ProfileDTO>> createProfile(
            @RequestBody ProfileDTO profileDTO,
            @AuthenticationPrincipal Jwt jwt
    ) {
        profileDTO.setUserId(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(profileCommandService.createProfile(profileDTO)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('UPDATE_PROFILE')")
    public ResponseEntity<ApiResponse<ProfileDTO>> updateProfile(
            @PathVariable("id") Long id,
            @RequestBody ProfileDTO profileDTO,
            @AuthenticationPrincipal Jwt jwt
    ) {
        // Verify ownership
        ProfileDTO existingProfile = profileQueryService.getProfileById(id);
        if (!existingProfile.getUserId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("You don't have permission to update this profile"));
        }
        
        return ResponseEntity.ok(ApiResponse.success(profileCommandService.updateProfile(id, profileDTO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') and hasAuthority('DELETE_PROFILE')")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        // Verify ownership or admin role
        if (!jwt.getClaimAsStringList("roles").contains("ROLE_ADMIN")) {
            ProfileDTO existingProfile = profileQueryService.getProfileById(id);
            if (!existingProfile.getUserId().equals(jwt.getSubject())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        
        profileCommandService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_USER') and hasAuthority('UPDATE_PROFILE')")
    public ResponseEntity<Void> updateProfileStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") Integer status,
            @AuthenticationPrincipal Jwt jwt
    ) {
        // Verify ownership
        ProfileDTO existingProfile = profileQueryService.getProfileById(id);
        if (!existingProfile.getUserId().equals(jwt.getSubject())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        profileCommandService.updateProfileStatus(id, status);
        return ResponseEntity.noContent().build();
    }
} 