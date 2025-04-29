package com.nanoit.agent.application;

import com.nanoit.agent.domain.Message;

public interface KtTransportOutputPort extends TransportOutputPort {
    // 실제 API 호출 로직이 들어가야함
    boolean send(Message message);
}
