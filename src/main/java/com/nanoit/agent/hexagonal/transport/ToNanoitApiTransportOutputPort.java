package com.nanoit.agent.hexagonal.transport;

import com.nanoit.agent.application.TransportOutputPort;
import org.springframework.stereotype.Component;

@Component
public class ToNanoitApiTransportOutputPort implements TransportOutputPort {

    @Override
    public void send(String phoneNumber, String message) {
        System.out.println("[NanoitAPI] 문자 전송: " + phoneNumber + " - " + message);
    }
}
