package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;

public interface NanoitTransportOutputPort extends TransportOutputPort {
    boolean send(Message message);
}
