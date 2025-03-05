package com.goodjob.posting.mapper;

import com.goodjob.posting.dto.JobPostingRequest;
import com.goodjob.posting.dto.JobPostingResponse;
import com.goodjob.posting.entity.JobPosting;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T00:03:05+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class JobPostingMapperImpl implements JobPostingMapper {

    @Override
    public JobPostingResponse toResponse(JobPosting jobPosting) {
        if ( jobPosting == null ) {
            return null;
        }

        JobPostingResponse.JobPostingResponseBuilder jobPostingResponse = JobPostingResponse.builder();

        jobPostingResponse.applicationCount( jobPosting.getApplicationCount() );
        jobPostingResponse.companyName( jobPosting.getCompanyName() );
        jobPostingResponse.createdAt( jobPosting.getCreatedAt() );
        jobPostingResponse.currency( jobPosting.getCurrency() );
        jobPostingResponse.description( jobPosting.getDescription() );
        jobPostingResponse.educationLevel( jobPosting.getEducationLevel() );
        jobPostingResponse.employerId( jobPosting.getEmployerId() );
        jobPostingResponse.experienceLevel( jobPosting.getExperienceLevel() );
        jobPostingResponse.expiresAt( jobPosting.getExpiresAt() );
        jobPostingResponse.id( jobPosting.getId() );
        jobPostingResponse.jobId( jobPosting.getJobId() );
        jobPostingResponse.jobType( jobPosting.getJobType() );
        jobPostingResponse.location( jobPosting.getLocation() );
        jobPostingResponse.maxSalary( jobPosting.getMaxSalary() );
        jobPostingResponse.minSalary( jobPosting.getMinSalary() );
        jobPostingResponse.salaryPeriod( jobPosting.getSalaryPeriod() );
        jobPostingResponse.status( jobPosting.getStatus() );
        jobPostingResponse.title( jobPosting.getTitle() );
        jobPostingResponse.updatedAt( jobPosting.getUpdatedAt() );
        jobPostingResponse.views( jobPosting.getViews() );

        return jobPostingResponse.build();
    }

    @Override
    public JobPosting toEntity(JobPostingRequest request) {
        if ( request == null ) {
            return null;
        }

        JobPosting.JobPostingBuilder jobPosting = JobPosting.builder();

        jobPosting.companyName( request.getCompanyName() );
        jobPosting.currency( request.getCurrency() );
        jobPosting.description( request.getDescription() );
        jobPosting.educationLevel( request.getEducationLevel() );
        jobPosting.experienceLevel( request.getExperienceLevel() );
        jobPosting.expiresAt( request.getExpiresAt() );
        jobPosting.jobId( request.getJobId() );
        jobPosting.jobType( request.getJobType() );
        jobPosting.location( request.getLocation() );
        jobPosting.maxSalary( request.getMaxSalary() );
        jobPosting.minSalary( request.getMinSalary() );
        jobPosting.salaryPeriod( request.getSalaryPeriod() );
        jobPosting.title( request.getTitle() );

        return jobPosting.build();
    }

    @Override
    public JobPosting updateEntity(JobPostingRequest request, JobPosting jobPosting) {
        if ( request == null ) {
            return jobPosting;
        }

        if ( request.getCompanyName() != null ) {
            jobPosting.setCompanyName( request.getCompanyName() );
        }
        if ( request.getCurrency() != null ) {
            jobPosting.setCurrency( request.getCurrency() );
        }
        if ( request.getDescription() != null ) {
            jobPosting.setDescription( request.getDescription() );
        }
        if ( request.getEducationLevel() != null ) {
            jobPosting.setEducationLevel( request.getEducationLevel() );
        }
        if ( request.getExperienceLevel() != null ) {
            jobPosting.setExperienceLevel( request.getExperienceLevel() );
        }
        if ( request.getExpiresAt() != null ) {
            jobPosting.setExpiresAt( request.getExpiresAt() );
        }
        if ( request.getJobId() != null ) {
            jobPosting.setJobId( request.getJobId() );
        }
        if ( request.getJobType() != null ) {
            jobPosting.setJobType( request.getJobType() );
        }
        if ( request.getLocation() != null ) {
            jobPosting.setLocation( request.getLocation() );
        }
        if ( request.getMaxSalary() != null ) {
            jobPosting.setMaxSalary( request.getMaxSalary() );
        }
        if ( request.getMinSalary() != null ) {
            jobPosting.setMinSalary( request.getMinSalary() );
        }
        if ( request.getSalaryPeriod() != null ) {
            jobPosting.setSalaryPeriod( request.getSalaryPeriod() );
        }
        if ( request.getTitle() != null ) {
            jobPosting.setTitle( request.getTitle() );
        }

        return jobPosting;
    }
}
