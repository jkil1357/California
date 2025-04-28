package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;

public interface MessageInputPort {

    void send(Message message);
}
