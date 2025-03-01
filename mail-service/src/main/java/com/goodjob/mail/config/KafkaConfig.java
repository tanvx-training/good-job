package com.goodjob.mail.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka configuration for the mail service.
 */
@Configuration
public class KafkaConfig {

    @Value("${mail-service.kafka.topics.email-events}")
    private String emailEventsTopic;

    /**
     * Create the email events topic.
     *
     * @return the topic
     */
    @Bean
    public NewTopic emailEventsTopic() {
        return TopicBuilder.name(emailEventsTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}