package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;

public class validateMessage { // ✅ 클래스 선언 추가!

    public static void validateMessage(Message message) {
        if (!(message instanceof ShortMessage shortMessage)) {
            throw new IllegalArgumentException("지원하지 않는 메시지 타입입니다.");
        }

        if (shortMessage.to() == null || !shortMessage.to().matches("^01[016789]-?\\d{3,4}-?\\d{4}$")) {
            throw new IllegalArgumentException("유효하지 않은 수신번호입니다.");
        }

        if (shortMessage.callbackNumber() == null || !shortMessage.callbackNumber().matches("^01[016789]-?\\d{3,4}-?\\d{4}$")) {
            throw new IllegalArgumentException("유효하지 않은 발신번호입니다.");
        }

        if (shortMessage.message() == null || shortMessage.message().isBlank()) {
            throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
        }
    }
}
