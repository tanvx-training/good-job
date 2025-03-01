package com.goodjob.posting.mapper;

import com.goodjob.posting.dto.JobPostingRequest;
import com.goodjob.posting.dto.JobPostingResponse;
import com.goodjob.posting.entity.JobPosting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper for JobPosting entity and DTOs.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobPostingMapper {

    /**
     * Convert a JobPosting entity to a JobPostingResponse DTO.
     *
     * @param jobPosting the JobPosting entity
     * @return the JobPostingResponse DTO
     */
    JobPostingResponse toResponse(JobPosting jobPosting);

    /**
     * Convert a JobPostingRequest DTO to a JobPosting entity.
     *
     * @param request the JobPostingRequest DTO
     * @return the JobPosting entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employerId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "applicationCount", ignore = true)
    @Mapping(target = "applications", ignore = true)
    JobPosting toEntity(JobPostingRequest request);

    /**
     * Update a JobPosting entity with values from a JobPostingRequest DTO.
     *
     * @param request    the JobPostingRequest DTO
     * @param jobPosting the JobPosting entity to update
     * @return the updated JobPosting entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employerId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "applicationCount", ignore = true)
    @Mapping(target = "applications", ignore = true)
    JobPosting updateEntity(JobPostingRequest request, @MappingTarget JobPosting jobPosting);
} 