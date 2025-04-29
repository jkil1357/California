package com.nanoit.agent.hexagonal.transport;

import com.nanoit.agent.application.TransportOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AligoSmsAdapter implements TransportOutputPort {

    @Override
    public void send(String phoneNumber, String message) {
        // 여기서 실제 통신사 API 호출 (RestTemplate이나 WebClient 사용 가능)
        System.out.println("문자 전송 요청: 번호=" + phoneNumber + ", 내용=" + message);

        // 예시: 실제 API 요청 코드는 여기 추가
        // RestTemplate 또는 WebClient로 POST 요청 보내기
    }
}
