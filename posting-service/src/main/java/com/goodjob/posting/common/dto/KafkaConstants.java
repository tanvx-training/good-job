package com.goodjob.posting.common.dto;

public class KafkaConstants {

  // Kafka topics
  public static final String POSTING_JOB_TOPIC = "job-service.posting";
  public static final String UN_POSTING_JOB_TOPIC = "job-service.unposting";

  // Kafka consumer group
  public static final String POSTING_SERVICE_GROUP_ID = "posting-service-group";
  public static final String UN_POSTING_SERVICE_GROUP_ID = "un-posting-service-group";
}
