package com.nanoit.agent.hexagonal.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoit.agent.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageSerializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public byte[] toBytes(Message message) throws Exception {
        return objectMapper.writeValueAsBytes(message);
    }
}