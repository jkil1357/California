package com.nanoit.agent.hexagonal.transport;

import com.nanoit.agent.application.PersistenceOutputPort;
import com.nanoit.agent.domain.ShortMessage;
import com.nanoit.agent.hexagonal.transport.dto.NanoitResult;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@EnableScheduling
@Component
public class ResultScheduler {

    private final PersistenceOutputPort outputPort;

    @Scheduled(fixedDelay = 10 * 1000L)
    public void scheduled() {
        NanoitResult results = null;

        outputPort.update(ShortMessage.builder()
                .id()
                .build());
    }
}
