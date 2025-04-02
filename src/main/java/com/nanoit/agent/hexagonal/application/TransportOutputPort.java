package com.nanoit.agent.hexagonal.application;

import com.nanoit.agent.hexagonal.domain.Message;

public interface TransportOutputPort {

    void send(Message message);
}
