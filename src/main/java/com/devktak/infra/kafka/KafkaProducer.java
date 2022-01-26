package com.devktak.infra.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC = "kafka-tak";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
}
