package net.da.net.kafkatest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class KafkaTestApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        SpringApplication.run(KafkaTestApplication.class, args);
        var props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.18.250.115:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "clientId");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        var producer = new KafkaProducer<String, String>(props);
        var topic = "kafka-test";
        producer.send(new ProducerRecord<>(topic, "Hello BIG Kafka")).get();

    }

}
