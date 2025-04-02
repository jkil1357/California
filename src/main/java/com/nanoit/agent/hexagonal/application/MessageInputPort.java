package com.nanoit.agent.hexagonal.application;

import com.nanoit.agent.hexagonal.domain.Message;

public interface MessageInputPort {

    void send(Message message);
}
