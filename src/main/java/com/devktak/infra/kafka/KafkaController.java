package com.devktak.infra.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KafkaController {

    private final KafkaProducer producer;

    @PostMapping("/kafka")
    public String sendMessage(@RequestBody Map<String, String> param) {
        String message = param.get("message");
        log.info("< Controller message = {}", message);

        this.producer.sendMessage("작성시간 = " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n작성내용 = " + message + "\n");

        return "success";
    }
}
