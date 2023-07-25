package com.techeer.port.voilio.global.config.kafka;

import com.techeer.port.voilio.domain.chat.dto.ChatMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

  @Value(value = "${spring.kafka.producer.bootstrap-servers}")
  private String bootstrapServer;

  @Bean
  public KafkaTemplate<String, ChatMessage> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  public ProducerFactory<String, ChatMessage> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigurations());
  }

  public Map<String, Object> producerConfigurations() {
    HashMap<String, Object> configurations = new HashMap<>();
    configurations.put(
        org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    configurations.put(
        org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    configurations.put(
        org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        JsonSerializer.class);
    return configurations;
  }
}
