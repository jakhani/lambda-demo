package com.intuit.craft.streaming;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;


public class TravelEventJob {

  public static void main(String[] args) {
    // Get job configuration
    Properties properties = ConfigFactory.loadConfiguration();

    // Spark config and contexts
    String sparkMaster = properties.getProperty(StreamingConstants.SPARK_MASTER);
    System.out.println("Test 1");
    /*SparkConf sparkConf = new SparkConf().setMaster(sparkMaster)
        .setAppName("StreamingExample").set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");*/
    SparkSession spark = SparkSession.builder().master("local[2]").appName("Java Spark Hive Example")
        .config("spark.cassandra.connection.host", "127.0.0.1").getOrCreate();
    System.out.println("Test 2");
    int batchInterval = Integer.parseInt(properties.getProperty(StreamingConstants.SPARK_BATCH_INTERVAL));

    JavaStreamingContext ctx = new JavaStreamingContext(spark.sparkContext().conf(), new Duration(batchInterval));
    // Create Kafka stream
    String groupId = properties.getProperty(StreamingConstants.KAFKA_GROUP_ID);
    String topic = properties.getProperty(StreamingConstants.KAFKA_TOPIC_NAME);
    Set<String> topics = new HashSet<String>();
    topics.add(topic);
    Map<String, Object> kafkaParams = new HashMap<String, Object>();
    kafkaParams.put("bootstrap.servers", properties.getProperty(StreamingConstants.KAFKA_BOOTSTRAP_SERVERS));
    kafkaParams.put("schema.registry.url", properties.getProperty(StreamingConstants.SCHEMA_REGISTRY_URL));
    kafkaParams.put("key.deserializer", StringDeserializer.class);
    kafkaParams.put("value.deserializer", StringDeserializer.class);
    kafkaParams.put("group.id", groupId);
    kafkaParams.put("auto.offset.reset", "latest");
    kafkaParams.put("enable.auto.commit", false);
    /*JavaPairInputDStream<byte[], byte[]> stream = KafkaUtils.createDirectStream(ctx, byte[].class, byte[].class, DefaultDecoder.class, DefaultDecoder.class,kafkaParams, topics);

     */
    JavaInputDStream<ConsumerRecord<String, String>> stream =
        KafkaUtils.createDirectStream(
            ctx,
            LocationStrategies.PreferConsistent(),
            ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
        );
    System.out.println(stream.count());
    ctx.start();
    try {
      ctx.awaitTermination();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
