package com.nanoit.agent.hexagonal.adapter.transport;

import com.nanoit.agent.hexagonal.application.TransportOutputPort;
import com.nanoit.agent.hexagonal.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class NettyTransportOutputPort implements TransportOutputPort {

    /**
     * Netty 구현체가 위치해야 하며 전달 받은 도메인 영역의 Message를 Byte Array로 변환해 외부 서버로 전송한다.
     */
    @Override
    public void send(Message message) {

    }
}
