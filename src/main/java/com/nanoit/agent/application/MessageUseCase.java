package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class MessageUseCase implements MessageInputPort {

    private final Map<String, ClientAdapter> clientAdapters;
    private final PersistenceOutputPort persistenceOutputPort;

    public MessageUseCase(Map<String, ClientAdapter> clientAdapters,
                          PersistenceOutputPort persistenceOutputPort) {
        this.clientAdapters = clientAdapters;
        this.persistenceOutputPort = persistenceOutputPort;
    }

    @Override
    public void send(Message message) {
        log.info("메시지 전송 요청: {}", message);

        if (message instanceof ShortMessage shortMsg) {
            if (!isValid(shortMsg)) return;

            String client = shortMsg.to().toUpperCase();
            ClientAdapter adapter = clientAdapters.get(client);

            if (adapter == null) {
                log.error("지원하지 않는 고객사: {}", client);
                persistenceOutputPort.update(shortMsg.withStatus("INVALID"), "지원하지 않는 고객사");
                return;
            }

            boolean result = adapter.send(shortMsg);
            String status = result ? "SENT" : "SENT_FAIL";
            persistenceOutputPort.update(shortMsg.withStatus(status));
        }
    }

    private boolean isValid(ShortMessage msg) {
        if (!isValidPhoneNumber(msg.receiveNumber())) {
            persistenceOutputPort.update(msg.withStatus("INVALID"), "수신번호 오류");
            return false;
        }
        if (!isValidPhoneNumber(msg.callbackNumber())) {
            persistenceOutputPort.update(msg.withStatus("INVALID"), "발신번호 오류");
            return false;
        }
        if (msg.to() == null || getByteLength(msg.to()) > 90) {
            persistenceOutputPort.update(msg.withStatus("INVALID"), "제목 오류");
            return false;
        }
        if (msg.message() == null || getByteLength(msg.message()) > 200) {
            persistenceOutputPort.update(msg.withStatus("INVALID"), "내용 오류");
            return false;
        }
        return true;
    }

    private boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^\\d{10,11}$");
    }

    private int getByteLength(String str) {
        return str.getBytes(StandardCharsets.UTF_8).length;
    }
}
