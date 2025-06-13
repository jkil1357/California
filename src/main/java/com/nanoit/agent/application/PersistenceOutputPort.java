package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;

public interface PersistenceOutputPort {

    void update(Message message);
    void update(Message message, String result);
}
