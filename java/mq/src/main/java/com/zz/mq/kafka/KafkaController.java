package com.zz.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/kafka")
@Slf4j
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/publish/{message}")
    public void publish(@PathVariable("message") String message) {
        log.info("get message " + message);
        kafkaProducer.produce("users", message);
    }
}
