package com.nanoit.agent.hexagonal.data;

import com.nanoit.agent.hexagonal.data.common.service.ShortMessageService;
import com.nanoit.agent.application.PersistenceOutputPort;
import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class H2PersistenceOutputPort implements PersistenceOutputPort {

    private final ShortMessageService service;

    public H2PersistenceOutputPort(ShortMessageService service) {
        this.service = service;
    }

    /**
     * JPA 구현체 Hibernate를 사용할 수 있는 repository, service 영역의 구현체가 위치하며 실제 update를 진행한다.
     */
    @Override
    public void update(Message message) {
        ShortMessageServiceEntity entity = new ShortMessageServiceEntity();
        entity.setId(message.id());
        entity.setReceiveNumber(message.receiveNumber());
        entity.setCallbackNumber(message.callbackNumber());
        entity.setMessage(message.message());
        
        if (message instanceof ShortMessage shortMessage) {
            entity.setStatus(shortMessage.status());
            entity.setTo(shortMessage.to());
        }
        
        entity.setModifiedDateTime(LocalDateTime.now());
        service.update(entity);
    }
}
