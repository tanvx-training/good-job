package com.goodjob.posting.mapper;

import com.goodjob.posting.dto.JobPostingRequest;
import com.goodjob.posting.dto.JobPostingResponse;
import com.goodjob.posting.entity.JobPosting;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-12T23:36:46+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class JobPostingMapperImpl implements JobPostingMapper {

    @Override
    public JobPostingResponse toResponse(JobPosting jobPosting) {
        if ( jobPosting == null ) {
            return null;
        }

        JobPostingResponse.JobPostingResponseBuilder jobPostingResponse = JobPostingResponse.builder();

        jobPostingResponse.id( jobPosting.getId() );
        jobPostingResponse.jobId( jobPosting.getJobId() );
        jobPostingResponse.employerId( jobPosting.getEmployerId() );
        jobPostingResponse.title( jobPosting.getTitle() );
        jobPostingResponse.description( jobPosting.getDescription() );
        jobPostingResponse.companyName( jobPosting.getCompanyName() );
        jobPostingResponse.location( jobPosting.getLocation() );
        jobPostingResponse.jobType( jobPosting.getJobType() );
        jobPostingResponse.experienceLevel( jobPosting.getExperienceLevel() );
        jobPostingResponse.educationLevel( jobPosting.getEducationLevel() );
        jobPostingResponse.minSalary( jobPosting.getMinSalary() );
        jobPostingResponse.maxSalary( jobPosting.getMaxSalary() );
        jobPostingResponse.currency( jobPosting.getCurrency() );
        jobPostingResponse.salaryPeriod( jobPosting.getSalaryPeriod() );
        jobPostingResponse.status( jobPosting.getStatus() );
        jobPostingResponse.createdAt( jobPosting.getCreatedAt() );
        jobPostingResponse.updatedAt( jobPosting.getUpdatedAt() );
        jobPostingResponse.expiresAt( jobPosting.getExpiresAt() );
        jobPostingResponse.views( jobPosting.getViews() );
        jobPostingResponse.applicationCount( jobPosting.getApplicationCount() );

        return jobPostingResponse.build();
    }

    @Override
    public JobPosting toEntity(JobPostingRequest request) {
        if ( request == null ) {
            return null;
        }

        JobPosting.JobPostingBuilder jobPosting = JobPosting.builder();

        jobPosting.jobId( request.getJobId() );
        jobPosting.title( request.getTitle() );
        jobPosting.description( request.getDescription() );
        jobPosting.companyName( request.getCompanyName() );
        jobPosting.location( request.getLocation() );
        jobPosting.jobType( request.getJobType() );
        jobPosting.experienceLevel( request.getExperienceLevel() );
        jobPosting.educationLevel( request.getEducationLevel() );
        jobPosting.minSalary( request.getMinSalary() );
        jobPosting.maxSalary( request.getMaxSalary() );
        jobPosting.currency( request.getCurrency() );
        jobPosting.salaryPeriod( request.getSalaryPeriod() );
        jobPosting.expiresAt( request.getExpiresAt() );

        return jobPosting.build();
    }

    @Override
    public JobPosting updateEntity(JobPostingRequest request, JobPosting jobPosting) {
        if ( request == null ) {
            return jobPosting;
        }

        if ( request.getJobId() != null ) {
            jobPosting.setJobId( request.getJobId() );
        }
        if ( request.getTitle() != null ) {
            jobPosting.setTitle( request.getTitle() );
        }
        if ( request.getDescription() != null ) {
            jobPosting.setDescription( request.getDescription() );
        }
        if ( request.getCompanyName() != null ) {
            jobPosting.setCompanyName( request.getCompanyName() );
        }
        if ( request.getLocation() != null ) {
            jobPosting.setLocation( request.getLocation() );
        }
        if ( request.getJobType() != null ) {
            jobPosting.setJobType( request.getJobType() );
        }
        if ( request.getExperienceLevel() != null ) {
            jobPosting.setExperienceLevel( request.getExperienceLevel() );
        }
        if ( request.getEducationLevel() != null ) {
            jobPosting.setEducationLevel( request.getEducationLevel() );
        }
        if ( request.getMinSalary() != null ) {
            jobPosting.setMinSalary( request.getMinSalary() );
        }
        if ( request.getMaxSalary() != null ) {
            jobPosting.setMaxSalary( request.getMaxSalary() );
        }
        if ( request.getCurrency() != null ) {
            jobPosting.setCurrency( request.getCurrency() );
        }
        if ( request.getSalaryPeriod() != null ) {
            jobPosting.setSalaryPeriod( request.getSalaryPeriod() );
        }
        if ( request.getExpiresAt() != null ) {
            jobPosting.setExpiresAt( request.getExpiresAt() );
        }

        return jobPosting;
    }
}
