package com.nanoit.agent.hexagonal.data;

import com.nanoit.agent.domain.ShortMessage;
import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import com.nanoit.agent.hexagonal.data.common.service.ShortMessageService;
import com.nanoit.agent.application.PersistenceOutputPort;
import com.nanoit.agent.domain.Message;
import org.springframework.stereotype.Component;

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
        // message 를 entity 로 변환 후 status 및 기타 상태 업데이트

        // 1. DB에 데이터를 객체로 가져온다
        ShortMessageServiceEntity entity = service.findById(message.id());
        if (message instanceof ShortMessage shortMessage) {
            // Status 를 바꾼다
            entity.setStatus(shortMessage.status());
            // DB에 로우를 업데이트한다.
            service.update(entity);
        }
    }

    @Override
    public void update(Message message, String result) {
        // message 를 entity 로 변환 후 status 및 기타 상태 업데이트

        // 1. DB에 데이터를 객체로 가져온다
        ShortMessageServiceEntity entity = service.findById(message.id());
        if (message instanceof ShortMessage shortMessage) {
            // Status 를 바꾼다
            entity.setStatus(shortMessage.status());
            // 결과를 업데이트 한다
            entity.setResult(result);
            // DB에 로우를 업데이트한다.
            service.update(entity);
        }
    }
}
