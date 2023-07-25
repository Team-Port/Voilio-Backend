package com.techeer.port.voilio.global.config.kafka;

import com.techeer.port.voilio.domain.chat.dto.ChatMessage;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
  private String bootstrapServer;

  @Value(value = "${spring.kafka.consumer.group-id}")
  private String groupId;

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, ChatMessage>
      kafkaListenerContainerFactory() {
    return getContainerFactory(groupId, ChatMessage.class);
  }

  private <T> ConcurrentKafkaListenerContainerFactory<String, T> getContainerFactory(
      String groupId, Class<T> classType) {
    ConcurrentKafkaListenerContainerFactory<String, T> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(getKafkaConsumerFactory(groupId, classType));
    return factory;
  }

  private <T> DefaultKafkaConsumerFactory<String, T> getKafkaConsumerFactory(
      String groupId, Class<T> classType) {
    JsonDeserializer<T> deserializer = setDeserializer(classType);
    return new DefaultKafkaConsumerFactory<>(
        setConfig(groupId, deserializer), new StringDeserializer(), deserializer);
  }

  private <T> Map<String, Object> setConfig(String groupId, JsonDeserializer<T> deserializer) {
    Map<String, Object> configurations = new HashMap<>();

    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    configurations.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class);
    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        deserializer);
    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

    return configurations;
  }

  private <T> JsonDeserializer<T> setDeserializer(Class<T> classType) {
    JsonDeserializer<T> deserializer = new JsonDeserializer<>(classType);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);
    return deserializer;
  }
}
