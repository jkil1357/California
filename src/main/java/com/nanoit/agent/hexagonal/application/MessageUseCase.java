package com.nanoit.agent.hexagonal.application;

import com.nanoit.agent.hexagonal.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageUseCase implements MessageInputPort {

    private final TransportOutputPort transportOutputPort;
    private final PersistenceOutputPort persistenceOutputPort;

    public MessageUseCase(TransportOutputPort transportOutputPort, PersistenceOutputPort persistenceOutputPort) {
        this.transportOutputPort = transportOutputPort;
        this.persistenceOutputPort = persistenceOutputPort;
    }

    /**
     * 비즈니스 로직을 구현하고 유효한 메시지일 경우 transport 영역으로 전달한다.
     * 필요한 경우 persistence port로 업데이트를 진행한다.
     */
    @Override
    public void send(Message message) {

    }
}
