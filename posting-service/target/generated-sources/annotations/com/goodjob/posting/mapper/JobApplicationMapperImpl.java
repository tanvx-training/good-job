package com.goodjob.posting.mapper;

import com.goodjob.posting.dto.JobApplicationRequest;
import com.goodjob.posting.dto.JobApplicationResponse;
import com.goodjob.posting.entity.JobApplication;
import com.goodjob.posting.entity.JobPosting;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T00:03:05+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class JobApplicationMapperImpl implements JobApplicationMapper {

    @Autowired
    private JobPostingMapper jobPostingMapper;

    @Override
    public JobApplicationResponse toResponse(JobApplication jobApplication) {
        if ( jobApplication == null ) {
            return null;
        }

        JobApplicationResponse.JobApplicationResponseBuilder jobApplicationResponse = JobApplicationResponse.builder();

        jobApplicationResponse.postingId( jobApplicationJobPostingId( jobApplication ) );
        jobApplicationResponse.jobPosting( jobPostingMapper.toResponse( jobApplication.getJobPosting() ) );
        jobApplicationResponse.applicantId( jobApplication.getApplicantId() );
        jobApplicationResponse.applicantViewed( jobApplication.getApplicantViewed() );
        jobApplicationResponse.coverLetter( jobApplication.getCoverLetter() );
        jobApplicationResponse.createdAt( jobApplication.getCreatedAt() );
        jobApplicationResponse.employerViewed( jobApplication.getEmployerViewed() );
        jobApplicationResponse.id( jobApplication.getId() );
        jobApplicationResponse.resumeUrl( jobApplication.getResumeUrl() );
        jobApplicationResponse.status( jobApplication.getStatus() );
        jobApplicationResponse.updatedAt( jobApplication.getUpdatedAt() );

        return jobApplicationResponse.build();
    }

    @Override
    public JobApplication toEntity(JobApplicationRequest request) {
        if ( request == null ) {
            return null;
        }

        JobApplication.JobApplicationBuilder jobApplication = JobApplication.builder();

        jobApplication.coverLetter( request.getCoverLetter() );
        jobApplication.resumeUrl( request.getResumeUrl() );

        return jobApplication.build();
    }

    private Integer jobApplicationJobPostingId(JobApplication jobApplication) {
        if ( jobApplication == null ) {
            return null;
        }
        JobPosting jobPosting = jobApplication.getJobPosting();
        if ( jobPosting == null ) {
            return null;
        }
        Integer id = jobPosting.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
