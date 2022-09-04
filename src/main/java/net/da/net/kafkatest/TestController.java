package net.da.net.kafkatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    private KafkaTemplate<String, String> template;
    @Autowired
    private CounterService counterService;

    private final String topic = "kafka-test";

    @GetMapping("/send")
    ResponseEntity<String> sendRandomMessage() {
        template.send(topic, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/result")
    ResponseEntity<Integer> getCounterResult() {
        return new ResponseEntity<>(counterService.getResult(), HttpStatus.OK);
    }

}
