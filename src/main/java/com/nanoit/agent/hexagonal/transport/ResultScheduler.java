package com.nanoit.agent.hexagonal.transport;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.nanoit.agent.application.PersistenceOutputPort;
import com.nanoit.agent.domain.ShortMessage;
import com.nanoit.agent.hexagonal.transport.dto.NanoitResult;
import com.nanoit.agent.hexagonal.transport.dto.NanoitSms;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    }

     case ShortMessage shortMessage; -> {
        try {
            String ymd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            yield objectMapper.writeValueAsString(NanoitSms.builder()
                    .accountType("M")
                    .id("kyu462755@gmail.com")
                    .apiKey("7a1819fe914ab907c304b3583fc69a69")
                    .ymd("ymd")
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
