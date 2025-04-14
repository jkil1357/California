package com.nanoit.agent.hexagonal.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoit.agent.application.NanoitTransportOutputPort;
import com.nanoit.agent.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ToNanoitApiTransportOutputPort implements NanoitTransportOutputPort {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String nanoitApiUrl = "https://nanoit-api.example.com/sms"; // TODO: 실제 URL로 변경 필요

    public ToNanoitApiTransportOutputPort(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean send(Message message) {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("to", message.receiveNumber());
            requestBody.put("from", message.callbackNumber());
            requestBody.put("message", message.message());

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(nanoitApiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;
        } catch (Exception e) {
            log.error("Failed to send message to NANOIT: {}", e.getMessage(), e);
            return false;
        }
    }
}
