package com.goodjob.job.api.kafka;

import com.goodjob.common.application.dto.event.JobPostingEvent;
import com.goodjob.common.application.dto.message.KafkaMessage;
import com.goodjob.common.application.enums.EventType;
import com.goodjob.common.infrastructure.util.MessageBuilder;
import com.goodjob.job.infrastructure.common.dto.KafkaConstants;
import com.goodjob.job.infrastructure.common.enums.PostingType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPostingProducer {

  @Value("${spring.application.name}")
  private String serviceId;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void postJob(JobPostingEvent jobPostingEvent) {

    try {
      KafkaMessage<JobPostingEvent> message = MessageBuilder.build(
          serviceId,
          EventType.EVENT,
          PostingType.POSTING.getDescription(),
          jobPostingEvent
      );
      kafkaTemplate.send(KafkaConstants.POSTING_JOB_TOPIC, message);
      log.info("Produced a message to topic: {}, value: {}", KafkaConstants.POSTING_JOB_TOPIC,
          jobPostingEvent);
    } catch (Exception e) {
      log.error("Failed to produce the message to topic: {}", KafkaConstants.POSTING_JOB_TOPIC);
      e.printStackTrace();
    }
  }

  public void unPostJob(JobPostingEvent jobPostingEvent) {
    try {
      KafkaMessage<JobPostingEvent> message = MessageBuilder.build(
          serviceId,
          EventType.EVENT,
          PostingType.UN_POSTING.getDescription(),
          jobPostingEvent
      );
      kafkaTemplate.send(KafkaConstants.UN_POSTING_JOB_TOPIC, message);
      log.info("Produced a message to topic: {}, value: {}", KafkaConstants.UN_POSTING_JOB_TOPIC,
          jobPostingEvent);
    } catch (Exception e) {
      log.error("Failed to produce the message to topic: {}", KafkaConstants.UN_POSTING_JOB_TOPIC);
      e.printStackTrace();
    }
  }
}
