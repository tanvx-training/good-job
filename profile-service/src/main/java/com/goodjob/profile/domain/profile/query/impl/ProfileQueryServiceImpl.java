package com.goodjob.profile.domain.profile.query.impl;

import com.goodjob.common.application.dto.response.PageResponseDTO;
import com.goodjob.common.application.exception.ResourceNotFoundException;
import com.goodjob.profile.domain.client.ExperienceCompanyView;
import com.goodjob.profile.domain.client.ProfileSkillView;
import com.goodjob.profile.domain.profile.dto.*;
import com.goodjob.profile.domain.profile.entity.Education;
import com.goodjob.profile.domain.profile.entity.Experience;
import com.goodjob.profile.domain.profile.entity.Profile;
import com.goodjob.profile.domain.profile.entity.ProfileSkill;
import com.goodjob.profile.domain.profile.query.ProfileQueryService;
import com.goodjob.profile.domain.profile.repository.ProfileRepository;
import com.goodjob.profile.infrastructure.enums.ProficiencyLevel;
import com.goodjob.profile.infrastructure.enums.ProfileStatus;
import com.goodjob.profile.infrastructure.helper.ProfileHelper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileHelper profileHelper;

    private final ProfileRepository profileRepository;

    @Override
    public ProfileDTO getProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .map(this::convertFromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("profile", "id", profileId));
    }

    @Override
    public ProfileDTO getProfileByUserId(String userId) {
        return null;
    }

    @Override
    public PageResponseDTO<ProfileDTO> searchProfiles(ProfileSearchQuery query) {
        Specification<Profile> spec = Specification.where(null);
        String keyword = query.getKeyword();
        if (StringUtils.hasText(keyword)) {
            String likePattern = "%" + query.getKeyword().toLowerCase() + "%";

            spec = spec.or((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likePattern));

            spec = spec.or((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likePattern));

            spec = spec.or((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaBuilder.concat(
                                    criteriaBuilder.concat(root.get("firstName"), " "),
                                    root.get("lastName"))),
                            likePattern));

            spec = spec.or((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("headline")), likePattern));

            spec = spec.or((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), likePattern));

            spec = spec.or((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("summary")), likePattern));

            // Mở rộng tìm kiếm thông qua các mối quan hệ
            spec = spec.or((root, criteriaQuery, criteriaBuilder) -> {
                Join<Profile, Experience> experienceJoin = root.join("experiences", JoinType.LEFT);
                return criteriaBuilder.like(criteriaBuilder.lower(experienceJoin.get("title")), likePattern);
            });

            spec = spec.or((root, criteriaQuery, criteriaBuilder) -> {
                Join<Profile, Education> educationJoin = root.join("educations", JoinType.LEFT);
                return criteriaBuilder.like(criteriaBuilder.lower(educationJoin.get("schoolName")), likePattern);
            });
        }

        // Tạo đối tượng Pageable từ thông tin query
        String[] sortParams = query.getSort().split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<Profile> profilePage = profileRepository.findAll(spec, pageable);
        return new PageResponseDTO<>(profilePage.map(this::convertFromEntity));
    }

    private ProfileDTO convertFromEntity(Profile profile) {
        ProfileDTO.ProfileDTOBuilder builder = ProfileDTO.builder()
                .profileId(profile.getProfileId())
                .userId(profile.getUserId())
                .fullName(profile.getFirstName() + " " + profile.getLastName())
                .headline(Optional.of(profile.getHeadline()).orElse(Strings.EMPTY))
                .summary(Optional.of(profile.getSummary()).orElse(Strings.EMPTY))
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .location(Optional.of(profile.getLocation()).orElse(Strings.EMPTY))
                .profileImageUrl(Optional.of(profile.getProfileImageUrl()).orElse(Strings.EMPTY))
                .status(ProfileStatus.fromValue(profile.getProfileStatus()).getDescription())
                .certifications(profile.getCertifications()
                        .stream()
                        .map(c -> CertificationDTO.builder()
                                .name(c.getName())
                                .organization(c.getOrganization())
                                .issueDate(c.getIssueDate())
                                .expirationDate(c.getExpirationDate())
                                .credentialUrl(c.getCredentialUrl())
                                .build())
                        .map(this::summarizeCertificationInfo)
                        .toList())
                .educations(profile.getEducations()
                        .stream()
                        .map(e -> EducationDTO.builder()
                                .schoolName(e.getSchoolName())
                                .degree(e.getDegree())
                                .fieldOfStudy(e.getFieldOfStudy())
                                .startDate(e.getStartDate())
                                .endDate(e.getEndDate())
                                .current(e.getCurrent())
                                .description(e.getDescription())
                                .build())
                        .map(this::summarizeEducationInfo)
                        .toList());
        List<Integer> companyIdList = profile.getExperiences()
                .stream()
                .map(Experience::getCompanyId)
                .toList();
        if (!CollectionUtils.isEmpty(companyIdList)) {
            Map<Integer, ExperienceCompanyView> ecv = profileHelper.getBatchCompanies(companyIdList)
                    .stream()
                    .collect(Collectors.toMap(
                            ExperienceCompanyView::getId, e -> e
                    ));
            List<String> experiences = profile.getExperiences()
                    .stream()
                    .map(e -> ExperienceDTO.builder()
                            .companyName(ecv.get(e.getCompanyId()).getName())
                            .title(e.getTitle())
                            .location(e.getLocation())
                            .startDate(e.getStartDate())
                            .endDate(e.getEndDate())
                            .current(e.getCurrent())
                            .description(e.getDescription())
                            .build()
                    )
                    .map(this::summarizeExperience)
                    .toList();
            builder.experiences(experiences);
        } else {
            builder.experiences(Collections.emptyList());
        }
        List<Integer> skillIdList = profile.getProfileSkills()
                .stream()
                .map(ProfileSkill::getSkillId)
                .toList();
        if (!CollectionUtils.isEmpty(skillIdList)) {
            Map<Integer, ProfileSkillView> psv = profileHelper.getSkills(skillIdList)
                    .stream()
                    .collect(Collectors.toMap(
                            ProfileSkillView::getId, s -> s
                    ));
            List<String> skills = profile.getProfileSkills()
                    .stream()
                    .map(s -> SkillDTO.builder()
                            .abbreviation(psv.get(s.getSkillId()).getAbbreviation())
                            .name(psv.get(s.getSkillId()).getName())
                            .proficiencyLevel(s.getProficiencyLevel())
                            .build())
                    .map(this::summarizeSkillInfo)
                    .toList();
            builder.skills(skills);
        } else {
            builder.skills(Collections.emptyList());
        }

        return builder.build();
    }

    private String summarizeExperience(ExperienceDTO experienceDTO) {
        StringBuilder sb = new StringBuilder();

        sb.append("Company Name: ")
                .append(Objects.nonNull(experienceDTO.getCompanyName())
                ? experienceDTO.getCompanyName() : "N/A")
                .append("\n");
        sb.append("Title: ").append(Objects.nonNull(experienceDTO.getTitle())
                ? experienceDTO.getCompanyName() : "N/A")
                .append("\n");
        sb.append("Location: ").append(Objects.nonNull(experienceDTO.getLocation())
                ? experienceDTO.getLocation() : "N/A")
                .append("\n");
        sb.append("Start Date: ").append(Objects.nonNull(experienceDTO.getStartDate())
                ? formatDate(experienceDTO.getStartDate()) : "N/A")
                .append("\n");
        sb.append("End Date: ").append(Objects.nonNull(experienceDTO.getEndDate())
                ? formatDate(experienceDTO.getEndDate()) : "N/A")
                .append("\n");
        sb.append("Current: ").append(experienceDTO.getCurrent() ? "Yes" : "No")
                .append("\n");
        sb.append("Description: ").append(Objects.nonNull(experienceDTO.getDescription())
                        ? experienceDTO.getDescription() : "N/A")
                .append("\n");

        return sb.toString();
    }

    public String summarizeSkillInfo(SkillDTO skillDTO) {
        StringBuilder sb = new StringBuilder();

        sb.append("Abbreviation: ").append(Objects.nonNull(skillDTO.getAbbreviation())
                ? skillDTO.getAbbreviation() : "N/A").append("\n");
        sb.append("Name: ").append(Objects.nonNull(skillDTO.getName())
                ? skillDTO.getName() : "N/A").append("\n");
        sb.append("Proficiency Level: ").append(Objects.nonNull(skillDTO.getProficiencyLevel())
                ? ProficiencyLevel.fromValue(skillDTO.getProficiencyLevel()).getDescription() : "N/A").append("\n");

        return sb.toString();
    }

    public String summarizeCertificationInfo(CertificationDTO certificationDTO) {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(Optional.ofNullable(certificationDTO.getName()).orElse("N/A")).append("\n");
        sb.append("Organization: ").append(Optional.ofNullable(certificationDTO.getOrganization()).orElse("N/A")).append("\n");
        sb.append("Issue Date: ").append(Objects.nonNull(certificationDTO.getIssueDate())
            ? formatDate(certificationDTO.getIssueDate()) : "N/A").append("\n");
        sb.append("Expiration Date: ").append(Objects.nonNull(certificationDTO.getExpirationDate())
                ? formatDate(certificationDTO.getExpirationDate()) : "N/A").append("\n");
        sb.append("Credential URL: ").append(Optional.ofNullable(certificationDTO.getCredentialUrl()).orElse("N/A")).append("\n");

        return sb.toString();
    }

    public String summarizeEducationInfo(EducationDTO educationDTO) {
        StringBuilder sb = new StringBuilder();

        sb.append("School Name: ").append(Optional.ofNullable(educationDTO.getSchoolName()).orElse("N/A")).append("\n");
        sb.append("Degree: ").append(Optional.ofNullable(educationDTO.getDegree()).orElse("N/A")).append("\n");
        sb.append("Field of Study: ").append(Optional.ofNullable(educationDTO.getFieldOfStudy()).orElse("N/A")).append("\n");
        String start = formatDate(educationDTO.getStartDate());
        String end;
        if (Objects.nonNull(educationDTO.getCurrent()) && educationDTO.getCurrent()) {
            end = "Present";
        } else {
            end = formatDate(educationDTO.getEndDate());
        }
        sb.append("Start Date: ").append(start).append("\n");
        sb.append("End Date: ").append(end).append("\n");
        sb.append("Description: ").append(Optional.ofNullable(educationDTO.getDescription()).orElse("N/A")).append("\n");

        return sb.toString();
    }

    // Phương thức phụ để định dạng ngày tháng
    private String formatDate(Long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
