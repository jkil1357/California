package com.nanoit.agent.hexagonal.transport;

import com.nanoit.agent.application.TransportOutputPort;
import com.nanoit.agent.domain.Message;
import org.springframework.stereotype.Component;

@Component
public class NettyTransportOutputPort implements TransportOutputPort {

    private final MessageSerializer serializer;

    public NettyTransportOutputPort(MessageSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public boolean send(Message message) {
        try {
            byte[] data = serializer.toBytes(message);
            new NettyClient().send("127.0.0.1", 9001, data); // 필요시 IP/포트 수정
            return true;
        } catch (Exception e) {
            System.err.println("Netty 전송 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
