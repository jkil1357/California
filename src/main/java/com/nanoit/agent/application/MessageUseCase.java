package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageUseCase extends MessageInputPort {

    private final KtTransportOutputPort ktTransportOutputPort;
    private final NanoitTransportOutputPort nanoitTransportOutputPort;
    private final PersistenceOutputPort persistenceOutputPort;

    public MessageUseCase(KtTransportOutputPort ktTransportOutputPort, NanoitTransportOutputPort nanoitTransportOutputPort, PersistenceOutputPort persistenceOutputPort) {
        this.ktTransportOutputPort = ktTransportOutputPort;
        this.nanoitTransportOutputPort = nanoitTransportOutputPort;
        this.persistenceOutputPort = persistenceOutputPort;
    }

    @Override
    public void send(Message message) {
        log.info("{}", message);
        validateMessage.validateMessage(message);


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
