package net.da.net.kafkatest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private CounterService counterService;

    @KafkaListener(topics = {"kafka-test"}, concurrency = "10")
    void topicListener(ConsumerRecord<String, String> record) {
        counterService.increment();
    }
}
