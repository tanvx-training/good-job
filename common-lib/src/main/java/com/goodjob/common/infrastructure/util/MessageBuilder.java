package com.goodjob.common.infrastructure.util;

import com.goodjob.common.application.dto.message.KafkaMessage;
import com.goodjob.common.application.dto.message.MessageMeta;
import com.goodjob.common.application.enums.EventType;
import java.util.UUID;

public class MessageBuilder {

  public static <T> KafkaMessage<T> build(
      String serviceId, EventType eventType, String messageCode, T payload) {
    KafkaMessage<T> message = new KafkaMessage<>();
    MessageMeta meta = MessageMeta.builder()
        .messageId(generateMessageId())
        .serviceId(serviceId)
        .type(eventType)
        .timestamp(System.currentTimeMillis())
        .build();
    message.setMeta(meta);
    message.setMessageCode(messageCode);
    message.setPayload(payload);
    return message;
  }

  public static String generateMessageId() {

    return UUID.randomUUID().toString().replace("_", "");
  }
}
