package com.nanoit.agent.hexagonal.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoit.agent.application.NanoitTransportOutputPort;
import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import com.nanoit.agent.hexagonal.transport.dto.NanoitResponse;
import com.nanoit.agent.hexagonal.transport.dto.NanoitSms;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 *
 */
@Slf4j
@AllArgsConstructor
@Component
public class ToNanoitApiTransportOutputPort implements NanoitTransportOutputPort {

    private final ObjectMapper objectMapper;

    // 실제 API 호출 로직이 들어가야함
    @Override
    public boolean send(Message message) {
        // 1. HttpClient 생성
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            // 2. 전송할 JSON 데이터
            String body = messageToJsonString(message);

            // 3. HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.funsms.kr/v3/biz/sms"))
                    .header("Content-Type", "application/json;charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            // 4. 요청 보내고 응답 받기
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. 결과 출력
            log.info("status.code={} response.body={}", response.statusCode(), response.body());
            if (response.statusCode() == 200) {
                NanoitResponse nanoitResponse = objectMapper.readValue(response.body(), NanoitResponse.class);
                if (nanoitResponse.code() != null && nanoitResponse.code().equals("0")) {
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            log.error("exception", e);
            return false;
        }
    }

    private String messageToJsonString(Message message) {
        return switch (message) {
            case ShortMessage shortMessage -> {
                try {
                    yield objectMapper.writeValueAsString(NanoitSms.builder()
                            .accountType("M")
                            .id("kyu462755@gmail.com")
                            .apiKey("7a1819fe914ab907c304b3583fc69a69")
                            .msg(shortMessage.message())
                            .callNumber(shortMessage.callbackNumber())
                            .recvData(List.of(NanoitSms.RecvData.builder()
                                    .recvId(shortMessage.id())
                                    .recvNumber(shortMessage.receiveNumber())
                                    .build()))
                            .build());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> throw new UnsupportedOperationException();
        };
    }
}
