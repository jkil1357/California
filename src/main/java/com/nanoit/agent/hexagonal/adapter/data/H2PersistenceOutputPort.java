package com.nanoit.agent.hexagonal.adapter.data;

import com.nanoit.agent.hexagonal.application.PersistenceOutputPort;
import com.nanoit.agent.hexagonal.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class H2PersistenceOutputPort implements PersistenceOutputPort {

    /**
     * JPA 구현체 Hibernate를 사용할 수 있는 repository, service 영역의 구현체가 위치하며 실제 update를 진행한다.
     */
    @Override
    public void update(Message message) {

    }
}
