package com.nanoit.agent.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageUseCase implements MessageInputPort {

    private final TransportOutputPort transportOutputPort;

    @Override
    public void sendSms(String phoneNumber, String message) {
        transportOutputPort.send(phoneNumber, message);
    }
}
