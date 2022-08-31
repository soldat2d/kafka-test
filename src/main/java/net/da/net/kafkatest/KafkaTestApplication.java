package net.da.net.kafkatest;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@SpringBootApplication
public class KafkaTestApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
//        SpringApplication.run(KafkaTestApplication.class, args);
        var topic = "kafka-test";
        var producer = new MyProducer(topic);
        IntStream.range(0, 100).forEach( i ->
                producer.send(i.toString));

        producer.close();
    }

}

class MyConsumer implements Closeable {

    private String topic;
    private KafkaConsumer<String, String> consumer;

    MyConsumer(String topic) {
        this.topic = topic;
        this.consumer = getConsumer();
    }

    private KafkaConsumer<String, String> getConsumer() {
        var props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.18.249.202:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "clientId");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaConsumer(props);
    }

    public void consume(Consumer<ConsumerRecord<String, String>> recordConsumer) {
        new Thread(() -> {
            while (true) {
                var records = consumer.poll(Duration.ofSeconds(1));
                records.forEach(record -> recordConsumer.accept(record));
            }
        }).start();
    }

    @Override
    public void close() throws IOException {
        consumer.close();
    }
}

class MyProducer implements Closeable {

    private String topic;
    private KafkaProducer<String, String> producer;

    MyProducer(String topic) {
        this.topic = topic;
        this.producer = getProducer();
    }

    private KafkaProducer<String, String> getProducer() {
        var props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.18.249.202:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "clientId");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer(props);
    }

    public void send(String key, String value) throws ExecutionException, InterruptedException {
        producer.send(new ProducerRecord<>(topic, key, value)).get();
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}