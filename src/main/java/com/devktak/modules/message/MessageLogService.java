package com.devktak.modules.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageLogService {

    private final MessageLogRepository messageLogRepository;

    public void addMessage(String message) {

        MessageLog messageLog = MessageLog.builder()
                        .message(message)
                        .messageWritingTime(LocalDateTime.now())
                        .build();

        messageLogRepository.save(messageLog);
    }
}
