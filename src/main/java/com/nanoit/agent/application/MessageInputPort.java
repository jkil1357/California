package com.nanoit.agent.application;

import com.nanoit.agent.application.MessageUseCase;
import com.nanoit.agent.domain.ShortMessage;
import com.nanoit.agent.application.KtTransportOutputPort;
import com.nanoit.agent.application.NanoitTransportOutputPort;
import com.nanoit.agent.application.PersistenceOutputPort;

public class MessageInputPort {

    public static void main(String[] args) {
        // 목 구현 (진짜 전송 안 되고 그냥 흉내만 냄)
        KtTransportOutputPort ktPort = message -> true;
        NanoitTransportOutputPort nanoitPort = message -> false;
        PersistenceOutputPort persistencePort = message -> System.out.println("DB 저장: " + message);

        // 실제 메시지 유스케이스 객체 생성
        MessageUseCase messageUseCase = new MessageUseCase(ktPort, nanoitPort, persistencePort);

        // 메시지 생성
        ShortMessage message = ShortMessage.builder()
                .id("msg001")
                .receiveNumber("010-1234-5678")
                .callbackNumber("010-8765-4321")
                .message("테스트입니다")
                .status("READY")
                .to("KT")
                .build();

        // 진짜 send() 실행
        messageUseCase.send(message);
    }
}
