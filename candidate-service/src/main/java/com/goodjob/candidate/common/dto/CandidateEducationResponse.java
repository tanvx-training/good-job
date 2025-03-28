package com.goodjob.candidate.common.dto;

import com.goodjob.candidate.entity.CandidateEducation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Data Transfer Object for returning candidate education data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateEducationResponse {

    private String id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentlyEnrolled;
    private Double grade;
    private String gradeScale;
    private String activitiesAndSocieties;
    private String description;
    private String formattedPeriod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Creates a CandidateEducationResponse from a CandidateEducation entity.
     *
     * @param education the education entity
     * @return the education response
     */
    public static CandidateEducationResponse fromEntity(CandidateEducation education) {
        if (education == null) {
            return null;
        }

        String formattedPeriod = formatPeriod(education.getStartDate(), education.getEndDate(), education.isCurrentlyEnrolled());

        return CandidateEducationResponse.builder()
                .id(education.getId().toString())
                .institution(education.getInstitution())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .location(education.getLocation())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .currentlyEnrolled(education.isCurrentlyEnrolled())
                .grade(education.getGrade())
                .gradeScale(education.getGradeScale())
                .activitiesAndSocieties(education.getActivitiesAndSocieties())
                .description(education.getDescription())
                .formattedPeriod(formattedPeriod)
                .createdAt(education.getCreatedAt())
                .updatedAt(education.getUpdatedAt())
                .build();
    }

    /**
     * Formats the period of education.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param currentlyEnrolled whether the candidate is currently enrolled
     * @return the formatted period
     */
    private static String formatPeriod(LocalDate startDate, LocalDate endDate, boolean currentlyEnrolled) {
        if (startDate == null) {
            return "";
        }

        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
        String startYear = startDate.format(yearFormatter);

        if (currentlyEnrolled) {
            return startYear + " - Present";
        } else if (endDate != null) {
            String endYear = endDate.format(yearFormatter);
            
            if (startYear.equals(endYear)) {
                return startYear;
            } else {
                return startYear + " - " + endYear;
            }
        } else {
            return startYear;
        }
    }
} 