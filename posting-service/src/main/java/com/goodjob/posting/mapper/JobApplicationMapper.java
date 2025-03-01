package com.goodjob.posting.mapper;

import com.goodjob.posting.dto.JobApplicationRequest;
import com.goodjob.posting.dto.JobApplicationResponse;
import com.goodjob.posting.entity.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper for JobApplication entity and DTOs.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {JobPostingMapper.class})
public interface JobApplicationMapper {

    /**
     * Convert a JobApplication entity to a JobApplicationResponse DTO.
     *
     * @param jobApplication the JobApplication entity
     * @return the JobApplicationResponse DTO
     */
    @Mapping(target = "postingId", source = "jobPosting.id")
    @Mapping(target = "jobPosting", source = "jobPosting")
    JobApplicationResponse toResponse(JobApplication jobApplication);

    /**
     * Convert a JobApplicationRequest DTO to a JobApplication entity.
     *
     * @param request the JobApplicationRequest DTO
     * @return the JobApplication entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jobPosting", ignore = true)
    @Mapping(target = "applicantId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employerViewed", ignore = true)
    @Mapping(target = "applicantViewed", ignore = true)
    JobApplication toEntity(JobApplicationRequest request);
} 