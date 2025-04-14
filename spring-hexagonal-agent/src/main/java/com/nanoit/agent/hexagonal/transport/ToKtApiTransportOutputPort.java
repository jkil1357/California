package com.nanoit.agent.hexagonal.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoit.agent.application.KtTransportOutputPort;
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
public class ToKtApiTransportOutputPort implements KtTransportOutputPort {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String ktApiUrl = "https://kt-api.example.com/sms"; // TODO: 실제 URL로 변경 필요

    public ToKtApiTransportOutputPort(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean send(Message message) {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("recipient", message.receiveNumber());
            requestBody.put("sender", message.callbackNumber());
            requestBody.put("content", message.message());

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ktApiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;
        } catch (Exception e) {
            log.error("Failed to send message to KT: {}", e.getMessage(), e);
            return false;
        }
    }
}
