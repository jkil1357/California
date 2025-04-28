package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageUseCase implements MessageInputPort {

    private final KtTransportOutputPort ktTransportOutputPort;
    private final NanoitTransportOutputPort nanoitTransportOutputPort;
    private final PersistenceOutputPort persistenceOutputPort;

    public MessageUseCase(KtTransportOutputPort ktTransportOutputPort, NanoitTransportOutputPort nanoitTransportOutputPort, PersistenceOutputPort persistenceOutputPort) {
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
        // 비즈니스 로직 1 - 수신번호가 전화번호가 맞는지
        // 비즈니스 로직 2 - 발신번호가 전화번호가 맞는지
        // 비즈니스 로직 3 - 메시지 내용이 있는지
        // 비즈니스 로직
        // 비즈니스 로직
        // 비즈니스 로직
        // 비즈니스 로직

        if (message instanceof ShortMessage shortMessage) {
            if (shortMessage.to().equalsIgnoreCase("KT")) {
                // kt로 전송하고 싶을때
                if (ktTransportOutputPort.send(message)) {
                    // 전송 성공
                    shortMessage.withStatus("OK");
                    persistenceOutputPort.update(shortMessage);
                } else {
                    // 전송 실패
                    shortMessage.withStatus("FAIL");
                    persistenceOutputPort.update(message);
                }
            } else if (shortMessage.to().equalsIgnoreCase("NANOIT")) {
                // 나노아이티로 전송하고 싶을때
                if (nanoitTransportOutputPort.send(message)) {
                    // 전송 성공
                    shortMessage.withStatus("OK");
                    persistenceOutputPort.update(shortMessage);
                } else {
                    // 전송 실패
                    shortMessage.withStatus("FAIL");
                    persistenceOutputPort.update(message);
                }
            }
        }
    }
}
