package com.intuit.craft.producer;

import Signup.Trial;
import com.intuit.craft.CraftConstants;
import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class TrialEventSender implements Runnable {
  private final Properties _properties;
  private final Producer<String, Trial> kafkaProducer;

  public TrialEventSender(Properties properties, Producer<String, Trial> kafkaProducer) {
    _properties = properties;
    this.kafkaProducer = kafkaProducer;
  }

  public void run(){
    // Taking kafka topic from configuration
    String topic = _properties.getProperty(CraftConstants.KAFKA_TOPIC_NAME);
    for (long nEvents = 0; nEvents < 10; nEvents++) {
      Trial event = EventGenerator.getNext();
      ProducerRecord<String, Trial>
          record = new ProducerRecord<String, Trial>(topic, event.getMessageDims().getItem().toString(), event);
      try {
        kafkaProducer.send(record).get();
      } catch(Exception exception) {
        System.out.println("Couldn't send events in Kafka: " + exception.getMessage());
      }
    }
  }
}
