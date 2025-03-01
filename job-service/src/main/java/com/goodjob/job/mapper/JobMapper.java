package com.goodjob.job.mapper;

import com.goodjob.job.command.dto.CreateJobCommand;
import com.goodjob.job.command.dto.CreateJobIndustryCommand;
import com.goodjob.job.command.dto.CreateJobSalaryCommand;
import com.goodjob.job.command.dto.CreateJobSkillCommand;
import com.goodjob.job.command.dto.UpdateJobCommand;
import com.goodjob.job.entity.Job;
import com.goodjob.job.entity.JobIndustry;
import com.goodjob.job.entity.JobIndustryId;
import com.goodjob.job.entity.JobSalary;
import com.goodjob.job.entity.JobSkill;
import com.goodjob.job.entity.JobSkillId;
import com.goodjob.job.query.dto.JobIndustryView;
import com.goodjob.job.query.dto.JobSalaryView;
import com.goodjob.job.query.dto.JobSkillView;
import com.goodjob.job.query.dto.JobView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Set;

/**
 * Mapper for Job entities and DTOs.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    /**
     * Map a CreateJobCommand to a Job entity.
     *
     * @param command    the command
     * @param employerId the employer ID
     * @return the job entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employerId", source = "employerId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "searchVector", ignore = true)
    @Mapping(target = "salary", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "industries", ignore = true)
    Job toEntity(CreateJobCommand command, String employerId);

    /**
     * Update a Job entity from an UpdateJobCommand.
     *
     * @param command the command
     * @param job     the job entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "searchVector", ignore = true)
    @Mapping(target = "salary", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "industries", ignore = true)
    void updateEntityFromCommand(UpdateJobCommand command, @MappingTarget Job job);

    /**
     * Map a CreateJobSalaryCommand to a JobSalary entity.
     *
     * @param command the command
     * @return the job salary entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "job", ignore = true)
    JobSalary toEntity(CreateJobSalaryCommand command);

    /**
     * Map a CreateJobSkillCommand to a JobSkill entity.
     *
     * @param command the command
     * @return the job skill entity
     */
    @Mapping(target = "id", expression = "java(new JobSkillId(null, command.getSkillId()))")
    @Mapping(target = "job", ignore = true)
    JobSkill toEntity(CreateJobSkillCommand command);

    /**
     * Map a CreateJobIndustryCommand to a JobIndustry entity.
     *
     * @param command the command
     * @return the job industry entity
     */
    @Mapping(target = "id", expression = "java(new JobIndustryId(null, command.getIndustryId()))")
    @Mapping(target = "job", ignore = true)
    JobIndustry toEntity(CreateJobIndustryCommand command);

    /**
     * Map a Job entity to a JobView DTO.
     *
     * @param job the job entity
     * @return the job view
     */
    JobView toView(Job job);

    /**
     * Map a JobSalary entity to a JobSalaryView DTO.
     *
     * @param salary the job salary entity
     * @return the job salary view
     */
    JobSalaryView toView(JobSalary salary);

    /**
     * Map a JobSkill entity to a JobSkillView DTO.
     *
     * @param skill the job skill entity
     * @return the job skill view
     */
    @Mapping(target = "skillId", source = "id.skillId")
    @Mapping(target = "skillName", ignore = true)
    @Mapping(target = "skillAbr", ignore = true)
    JobSkillView toView(JobSkill skill);

    /**
     * Map a JobIndustry entity to a JobIndustryView DTO.
     *
     * @param industry the job industry entity
     * @return the job industry view
     */
    @Mapping(target = "industryId", source = "id.industryId")
    @Mapping(target = "industryName", ignore = true)
    JobIndustryView toView(JobIndustry industry);

    /**
     * Map a set of JobSkill entities to a set of JobSkillView DTOs.
     *
     * @param skills the job skill entities
     * @return the job skill views
     */
    Set<JobSkillView> toSkillViews(Set<JobSkill> skills);

    /**
     * Map a set of JobIndustry entities to a set of JobIndustryView DTOs.
     *
     * @param industries the job industry entities
     * @return the job industry views
     */
    Set<JobIndustryView> toIndustryViews(Set<JobIndustry> industries);
} 