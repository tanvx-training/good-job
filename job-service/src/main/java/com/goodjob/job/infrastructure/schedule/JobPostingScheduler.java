package com.goodjob.job.infrastructure.schedule;

import com.goodjob.job.domain.job.repository.JobRepository;
import com.goodjob.job.domain.posting.command.PostingCommandService;
import com.goodjob.job.domain.posting.dto.CreatePostingCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPostingScheduler {

    private final JobRepository jobRepository;

    private final PostingCommandService postingCommandService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleCreateJobPostings() {
        jobRepository.findAll().forEach(job -> {
           log.info("Start create job posting {}", job);
           Long result = postingCommandService.createPostingJob(CreatePostingCommand.builder()
                           .jobId(job.getJobId())
                           .startTime(LocalDateTime.now())
                           .endTime(LocalDateTime.now().plusDays(30))
                   .build());
           log.info("End create job posting id {} - result is {}", job.getJobId(), result == 0 ? "success" : "fail");
        });
    }
}
