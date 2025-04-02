package com.nanoit.agent.hexagonal.application;

import com.nanoit.agent.hexagonal.domain.Message;

public interface PersistenceOutputPort {

    void update(Message message);
}
