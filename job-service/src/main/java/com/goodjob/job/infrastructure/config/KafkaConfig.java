package com.goodjob.job.infrastructure.config;

import com.goodjob.job.infrastructure.common.dto.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic postingTopic() {
        return TopicBuilder.name(KafkaConstants.POSTING_JOB_TOPIC)
                .partitions(6)
                .replicas(2)
                .configs(Map.of(
                        "retention.ms", "604800000", // Giữ message trong 7 ngày
                        "segment.bytes", "1073741824" // Segment size 1GB
                ))
                .build();
    }

    // Tạo topic "un-posting"
    @Bean
    public NewTopic unPostingTopic() {
        return TopicBuilder.name(KafkaConstants.UN_POSTING_JOB_TOPIC)
                .partitions(6)
                .replicas(2)
                .configs(Map.of(
                        "retention.ms", "604800000", // Giữ message trong 7 ngày
                        "segment.bytes", "1073741824" // Segment size 1GB
                ))
                .build();
    }
}
