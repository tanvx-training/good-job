package com.goodjob.profile.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for profile data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO implements Serializable {
    
    private Long profileId;
    private Long userId;
    private String fullName;
    private String headline;
    private String summary;
    private String email;
    private String phone;
    private String location;
    private String profileImageUrl;
    private String status;
    private List<String> experiences;
    private List<String> educations;
    private List<String> skills;
    private List<String> certifications;
} 