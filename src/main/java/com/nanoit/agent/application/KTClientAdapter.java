package com.nanoit.agent.application;

import com.nanoit.agent.domain.ShortMessage;
import org.springframework.stereotype.Component;

@Component("KT")
public class KTClientAdapter implements ClientAdapter {

    private final KtTransportOutputPort ktPort;

    public KTClientAdapter(KtTransportOutputPort ktPort) {
        this.ktPort = ktPort;
    }

    @Override
    public boolean send(ShortMessage message) {
        return ktPort.send(message);
    }
}

//KT 방식으로 메시지를 전송하는 구현체를 만듭니다.
