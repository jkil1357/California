package com.nanoit.agent.application;

import com.nanoit.agent.domain.ShortMessage;
import org.springframework.stereotype.Component;

@Component("NANOIT")
public class NanoitClientAdapter implements ClientAdapter {

    private final NanoitTransportOutputPort nanoitPort;

    public NanoitClientAdapter(NanoitTransportOutputPort nanoitPort) {
        this.nanoitPort = nanoitPort;
    }

    @Override
    public boolean send(ShortMessage message) {
        return nanoitPort.send(message);
    }
}
//나노아이티 방식으로 메시지를 전송하는 구현체를 만듭니다.
