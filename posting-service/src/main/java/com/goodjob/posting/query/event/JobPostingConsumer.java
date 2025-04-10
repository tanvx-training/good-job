package com.goodjob.posting.query.event;

import com.goodjob.common.dto.message.KafkaMessage;
import com.goodjob.posting.common.dto.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPostingConsumer {

    @KafkaListener(
            topics = KafkaConstants.POSTING_JOB_TOPIC,
            groupId = KafkaConstants.POSTING_SERVICE_GROUP_ID,
            properties = {"spring.json.value.default.type=com.goodjob.common.dto.message.KafkaMessage"},
            concurrency = "6"
    )
    public void consumePostingEvent(@Payload KafkaMessage<JobPostingEvent> event) {
        log.info("Consuming event: {}", event);
    }
}
