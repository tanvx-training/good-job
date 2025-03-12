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
    date = "2025-03-12T23:36:46+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
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
        jobApplicationResponse.id( jobApplication.getId() );
        jobApplicationResponse.applicantId( jobApplication.getApplicantId() );
        jobApplicationResponse.resumeUrl( jobApplication.getResumeUrl() );
        jobApplicationResponse.coverLetter( jobApplication.getCoverLetter() );
        jobApplicationResponse.status( jobApplication.getStatus() );
        jobApplicationResponse.createdAt( jobApplication.getCreatedAt() );
        jobApplicationResponse.updatedAt( jobApplication.getUpdatedAt() );
        jobApplicationResponse.employerViewed( jobApplication.getEmployerViewed() );
        jobApplicationResponse.applicantViewed( jobApplication.getApplicantViewed() );

        return jobApplicationResponse.build();
    }

    @Override
    public JobApplication toEntity(JobApplicationRequest request) {
        if ( request == null ) {
            return null;
        }

        JobApplication.JobApplicationBuilder jobApplication = JobApplication.builder();

        jobApplication.resumeUrl( request.getResumeUrl() );
        jobApplication.coverLetter( request.getCoverLetter() );

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
