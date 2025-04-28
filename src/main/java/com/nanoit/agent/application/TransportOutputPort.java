package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;

public interface TransportOutputPort {

    boolean send(Message message);
}
