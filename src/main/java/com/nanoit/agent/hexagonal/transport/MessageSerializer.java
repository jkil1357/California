package com.nanoit.agent.hexagonal.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoit.agent.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageSerializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public byte[] toBytes(Message message) throws Exception {
        String json = objectMapper.writeValueAsString(message); //확인용
        System.out.println("🔧 직렬화된 JSON: " + json); //확인용
        return objectMapper.writeValueAsBytes(message);
    }
}