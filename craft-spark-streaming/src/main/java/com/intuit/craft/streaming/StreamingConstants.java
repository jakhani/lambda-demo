package com.intuit.craft.streaming;

public class StreamingConstants {
  public static final String CONFIG_FILE_PATH = "craft-spark-streaming/src/main/resources/";
  public static final String CONFIG_FILE_NAME = "streaming-config.properties";

  // Properties from craft_config.properties file
  public static final String SCHEMA_REGISTRY_URL = "schema_registry_url";
  public static final String KAFKA_BOOTSTRAP_SERVERS = "kafka_bootstrap_servers";
  public static final String KAFKA_TOPIC_NAME = "kafka_topic_name";
  public static final String SPARK_MASTER = "spark_master";
  public static final String SPARK_BATCH_INTERVAL = "spark_batch_interval";
  public static final String KAFKA_GROUP_ID = "kafka_group_id";
  public static final String ZOOKEEPER_SERVER = "zookeeper_server";
}
