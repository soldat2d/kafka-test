package net.da.net.kafkatest;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    ProducerFactory<String, String> producerFactory (KafkaProperties properties) {
        return new DefaultKafkaProducerFactory(properties.buildProducerProperties());
    }

    @Bean
    KafkaTemplate<String, String> kafkaTemplate (ProducerFactory<String, String> factory) {
        return new KafkaTemplate(factory);
    }
}
