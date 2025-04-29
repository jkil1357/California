package com.nanoit.agent.hexagonal.data;

import com.nanoit.agent.application.MessageInputPort;
import com.nanoit.agent.domain.ShortMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SelectSchedulerInputAdapter {

    private final MessageInputPort messageInputPort;
    private final MessageRepository messageRepository;

    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    public void sendMessages() {
        List<ShortMessage> messages = messageRepository.findPendingMessages();

        messages.forEach(message ->
                messageInputPort.sendSms(message.receiveNumber(), message.message())
        );
    }
}
