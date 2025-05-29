package com.nanoit.agent.application;

import com.nanoit.agent.domain.LongMessage;
import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageUseCase implements MessageInputPort {

    private final KtTransportOutputPort ktTransportOutputPort;
    private final NanoitTransportOutputPort nanoitTransportOutputPort;
    private final PersistenceOutputPort persistenceOutputPort;

    // 정치인 관련 금지어 목록
    private static final List<String> BLOCKED_WORDS = List.of(
        "이재명", "김문수", "권영국", "이준석"
    );

    public MessageUseCase(KtTransportOutputPort ktTransportOutputPort,
                          NanoitTransportOutputPort nanoitTransportOutputPort,
                          PersistenceOutputPort persistenceOutputPort) {
        this.ktTransportOutputPort = ktTransportOutputPort;
        this.nanoitTransportOutputPort = nanoitTransportOutputPort;
        this.persistenceOutputPort = persistenceOutputPort;
    }

    /**
     * 비즈니스 로직을 구현하고 유효한 메시지일 경우 transport 영역으로 전달한다.
     * 필요한 경우 persistence port로 업데이트를 진행한다.
     */
    @Override
    public void send(Message message) {
        log.info("{}", message);

        if (message instanceof ShortMessage shortMessage) {
            // 1. 수신번호 유효성 검사 (숫자 10~11자리)
            if (!isValidPhoneNumber(shortMessage.receiveNumber())) {
                log.error("잘못된 수신번호: {}", shortMessage.receiveNumber());
                persistenceOutputPort.update(shortMessage.withStatus("INVALID"), "잘못된 수신번호");
                return;
            }

            // 2. 발신번호 유효성 검사
            if (!isValidPhoneNumber(shortMessage.callbackNumber())) {
                log.error("잘못된 발신번호: {}", shortMessage.callbackNumber());
                persistenceOutputPort.update(shortMessage.withStatus("INVALID"), "잘못된 발신번호");
                return;
            }

            // 3. 제목 유효성 검사 (null 아니고 90바이트 이하)
            if (shortMessage.to() == null || getByteLength(shortMessage.to()) > 90) {
                log.error("제목이 없거나 90바이트를 초과함. 제목: {}", shortMessage.to());
                persistenceOutputPort.update(shortMessage.withStatus("INVALID"), "제목 길이 초과");
                return;
            }

            // 4. 메시지 내용 유효성 검사 (null 아니고 200바이트 이하)
            if (shortMessage.message() == null || getByteLength(shortMessage.message()) > 200) {
                log.error("메시지 내용이 없거나 200바이트를 초과함. 메시지: {}", shortMessage.message());
                persistenceOutputPort.update(shortMessage.withStatus("INVALID"), "메시지 내용 오류");
                return;
            }

            // 5. 정치인 금지어 필터링
            if (containsBlockedWords(shortMessage.message())) {
                log.error("정치인 금지어 포함된 메시지입니다. 메시지: {}", shortMessage.message());
                persistenceOutputPort.update(shortMessage.withStatus("INVALID"), "정치인 이름 포함 금지");
                return;
            }

            // 6. 전송 분기 처리
            if (shortMessage.to().equalsIgnoreCase("KT")) {
                if (ktTransportOutputPort.send(shortMessage)) {
                    persistenceOutputPort.update(shortMessage.withStatus("SENT"));
                } else {
                    persistenceOutputPort.update(shortMessage.withStatus("SENT_FAIL"));
                }
            } else if (shortMessage.to().equalsIgnoreCase("NANOIT")) {
                if (nanoitTransportOutputPort.send(shortMessage)) {
                    persistenceOutputPort.update(shortMessage.withStatus("SENT"));
                } else {
                    persistenceOutputPort.update(shortMessage.withStatus("SENT_FAIL"));
                }
            } else {
                log.warn("지원되지 않는 전송 대상: {}", shortMessage.to());
                persistenceOutputPort.update(shortMessage.withStatus("INVALID"), "전송 대상 미지원");
            }
        }
    }

    private boolean isValidPhoneNumber(String number) {
        return number != null && number.matches(
            "^(\\d{9,11}|\\d{2,4}-\\d{3,4}-\\d{4}|\\d{4}-\\d{4})$"
        );
    }

    private int getByteLength(String str) {
        return str.getBytes(StandardCharsets.UTF_8).length;
    }

    private boolean containsBlockedWords(String message) {
        return BLOCKED_WORDS.stream().anyMatch(message::contains);
    }
}
