package com.goodjob.common.util;

import com.goodjob.common.dto.message.KafkaMessage;
import com.goodjob.common.dto.message.MessageMeta;
import com.goodjob.common.enums.EventType;
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
