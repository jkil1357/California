package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        log.info("Processing message: {}", message);
        
        if (!isValidPhoneNumber(message.receiveNumber())) {
            log.error("Invalid receive number: {}", message.receiveNumber());
            updateMessageStatus(message, "FAIL");
            return;
        }

        if (!isValidPhoneNumber(message.callbackNumber())) {
            log.error("Invalid callback number: {}", message.callbackNumber());
            updateMessageStatus(message, "FAIL");
            return;
        }

        if (!StringUtils.hasText(message.message())) {
            log.error("Empty message content");
            updateMessageStatus(message, "FAIL");
            return;
        }

        if (message instanceof ShortMessage) {
            processShortMessage((ShortMessage) message);
        }
    }

    private void processShortMessage(ShortMessage shortMessage) {
        boolean success = false;
        String destination = shortMessage.to().toUpperCase();
        
        if ("KT".equals(destination)) {
            success = ktTransportOutputPort.send(shortMessage);
        } else if ("NANOIT".equals(destination)) {
            success = nanoitTransportOutputPort.send(shortMessage);
        } else {
            log.error("Unknown destination: {}", shortMessage.to());
        }

        updateMessageStatus(shortMessage, success ? "OK" : "FAIL");
    }

    private void updateMessageStatus(Message message, String status) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;
            ShortMessage updatedMessage = shortMessage.withStatus(status);
            persistenceOutputPort.update(updatedMessage);
        }
    }

    private boolean isValidPhoneNumber(String number) {
        String regex = "^\\d{2,3}-?\\d{3,4}-?\\d{4}$";
        return number != null && number.matches(regex);
    }
}
