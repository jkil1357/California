package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;

import java.nio.charset.StandardCharsets;

public class validateMessage {

    public static void validateMessage(Message message) {
        if (!(message instanceof ShortMessage shortMessage)) {
            throw new IllegalArgumentException("지원하지 않는 메시지 타입입니다.");
        }

// 수신번호 검사
        if (shortMessage.getSenderNumber()== null || !shortMessage.getSenderNumber().matches("^01[016789]-?\\d{3,4}-?\\d{4}$")) {
            throw new IllegalArgumentException("유효하지 않은 수신번호입니다.");
        }

// 발신번호 검사
        if (shortMessage.getReceiverNumber() == null || !shortMessage.getReceiverNumber().matches("^01[016789]-?\\d{3,4}-?\\d{4}$")) {
            throw new IllegalArgumentException("유효하지 않은 발신번호입니다.");
        }

// 제목 유효성 검사
        if (shortMessage.getTitle() == null || shortMessage.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목이 비어있습니다.");
        }
        if (shortMessage.getTitle().getBytes(StandardCharsets.UTF_8).length > 90) {
            throw new IllegalArgumentException("제목은 90byte 이하이어야 합니다.");
        }

// 메시지 내용 유효성 검사
        if (shortMessage.getContent() == null || shortMessage.getContent().isBlank()) {
            throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
        }
        if (shortMessage.getContent().getBytes(StandardCharsets.UTF_8).length > 200) {
            throw new IllegalArgumentException("메시지 내용은 200byte 이하이어야 합니다.");
        }
    }
}
