package com.nanoit.agent.application;

import com.nanoit.agent.core.ValidateMessage;
import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.MessageStatus;
import com.nanoit.agent.port.input.MessageInputPort;
import com.nanoit.agent.port.output.KtTransportOutputPort;
import com.nanoit.agent.port.output.NanoitTransportOutputPort;
import com.nanoit.agent.port.output.PersistenceOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageUseCase implements MessageInputPort {

    private final KtTransportOutputPort ktTransportOutputPort;
    private final NanoitTransportOutputPort nanoitTransportOutputPort;
    private final PersistenceOutputPort persistenceOutputPort;
    private final ValidateMessage validateMessage;

    public MessageUseCase(
            KtTransportOutputPort ktTransportOutputPort,
            NanoitTransportOutputPort nanoitTransportOutputPort,
            PersistenceOutputPort persistenceOutputPort,
            ValidateMessage validateMessage
    ) {
        this.ktTransportOutputPort = ktTransportOutputPort;
        this.nanoitTransportOutputPort = nanoitTransportOutputPort;
        this.persistenceOutputPort = persistenceOutputPort;
        this.validateMessage = validateMessage;
    }

    /**
     * 비즈니스 로직을 구현하고 유효한 메시지일 경우 transport 영역으로 전달한다.
     * 필요한 경우 persistence port로 업데이트를 진행한다.
     */
    @Override
    public void send(Message message) {
        log.info("메시지 수신: {}", message);

        try {
            //유효성 검사
            validateMessage.validate(message);

            //분기 처리
            switch (message.to()) {
                case "KT" -> {
                    ktTransportOutputPort.send(message);
                    message.setStatus(MessageStatus.OK);
                    log.info("KT 전송 성공");
                }
                case "NANOIT" -> {
                    nanoitTransportOutputPort.send(message);
                    message.setStatus(MessageStatus.OK);
                    log.info("NANOIT 전송 성공");
                }
                default -> {
                    log.error("지원하지 않는 전송 대상: {}", message.to());
                    message.setStatus(MessageStatus.FAIL);
                }
            }

        } catch (Exception e) {
            
            message.setStatus(MessageStatus.FAIL);
            log.error("메시지 전송 실패: {}", e.getMessage(), e);
        }

        //DB 저장
        try {
            persistenceOutputPort.update(message);
            log.info("메시지 상태 저장 완료: {}", message.getStatus());
        } catch (Exception e) {
            log.error("메시지 상태 저장 실패: {}", e.getMessage(), e);
        }
    }
}
