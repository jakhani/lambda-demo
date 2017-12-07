package com.intuit.craft.producer;

import Signup.Trial;
import com.intuit.craft.CraftConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class TrialEventProducer {
  public static void main(String[] args) throws ExecutionException, InterruptedException {

    Properties properties = loadConfiguration();
    if (properties == null) {
      return;
    }
    String schemaUrl = properties.getProperty(CraftConstants.SCHEMA_REGISTRY_URL);

    Properties kafkaProperties = new Properties();
    // hardcoding the Kafka server URI for this example
    kafkaProperties.put("bootstrap.servers", properties.getProperty(CraftConstants.KAFKA_BROKER_SERVER));
    kafkaProperties.put("acks", "all");
    kafkaProperties.put("retries", 0);
    kafkaProperties.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
    kafkaProperties.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
    kafkaProperties.put("schema.registry.url", schemaUrl);

    Producer<String, Trial> producer = new KafkaProducer<String, Trial>(kafkaProperties);
    generateTrialEvents(properties, producer);
  }

  private static void generateTrialEvents(Properties properties, Producer<String, Trial> kafkaProducer) {
    ScheduledExecutorService notificationsThreadPool = Executors.newScheduledThreadPool(1);
    TrialEventSender trialEventSender = new TrialEventSender(properties, kafkaProducer);
    notificationsThreadPool.scheduleAtFixedRate(trialEventSender, 0, 1, TimeUnit.SECONDS);
  }

  private static Properties loadConfiguration() {
    Properties properties = null;
    InputStream input = null;
    try {
      input = new FileInputStream(CraftConstants.CONFIG_FILE_PATH + CraftConstants.CONFIG_FILE_NAME);
      properties = new Properties();
      properties.load(input);
    } catch (IOException ex) {
      System.out.println("Couldn't find configuration file. Please validate config file.");
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          System.out.println("Unable to close the InputStream for configuration file. : " + e.getMessage());
        }
      }
    }
    return properties;
  }
}
