package com.devktak.infra.kafka;

import com.devktak.modules.message.MessageLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final MessageLogService messageLogService;

    private final KafkaProducer producer;

    @PostMapping("/kafka")
    public String sendMessage(@RequestBody Map<String, String> param) {
        String message = param.get("message");

        messageLogService.addMessage(message);

        this.producer.sendMessage("작성시간 = " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n작성내용 = " + message + "\n");

        return "success";
    }
}
