package com.nanoit.agent.hexagonal.transport;

import com.nanoit.agent.application.KtTransportOutputPort;
import com.nanoit.agent.application.TransportOutputPort;
import com.nanoit.agent.domain.Message;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public class ToKtApiTransportOutputPort implements KtTransportOutputPort {

    // 실제 API 호출 로직이 들어가야함
    @Override
    public boolean send(Message message) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {

        }
        // 성공시
        return true;
        // 실패시
//        return false;
    }
}
