package com.nanoit.agent.hexagonal.transport;

import com.nanoit.agent.application.KtTransportOutputPort;
import com.nanoit.agent.domain.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ToKtApiTransportOutputPort implements KtTransportOutputPort {

    private final AtomicInteger counter;

    public ToKtApiTransportOutputPort() {
        this.counter = new AtomicInteger(0);
    }

    @Override
    public boolean send(Message message) {
        var count = counter.incrementAndGet();
        if (count % 10 == 0) {
            // 실패
            return false;
        } else {
            // 성공
            return true;
        }
    }
}
