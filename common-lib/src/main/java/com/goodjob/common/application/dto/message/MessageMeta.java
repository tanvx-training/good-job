package com.goodjob.common.application.dto.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.goodjob.common.application.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageMeta {

  protected String messageId;
  private String originalMessageId;
  private EventType type;
  private String serviceId;
  private long timestamp;
  private boolean autoRetry;
}
