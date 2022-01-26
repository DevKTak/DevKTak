package com.devktak.infra.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-tak", groupId = "kafka-tak")
    public void consume(String message) throws IOException {
        log.info("\n< Consumed Message >\n{}", message);
    }
}
