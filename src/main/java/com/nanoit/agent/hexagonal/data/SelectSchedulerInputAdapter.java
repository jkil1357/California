package com.nanoit.agent.hexagonal.data;

import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import com.nanoit.agent.hexagonal.data.common.service.ShortMessageService;
import com.nanoit.agent.application.MessageInputPort;
import com.nanoit.agent.domain.ShortMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 데이터 조회를 담당하며 조회된 데이터를 core 영역으로 전달한다.
 */
@Slf4j
@EnableScheduling
@Component

public class SelectSchedulerInputAdapter {

    private final ShortMessageService shortMessageService;
    private final MessageInputPort messageInputPort;

    public SelectSchedulerInputAdapter(ShortMessageService shortMessageService, MessageInputPort messageInputPort) {
        this.shortMessageService = shortMessageService;
        this.messageInputPort = messageInputPort;
    }

    @Scheduled(fixedDelay = 1000L)
    public void select() {
        log.info("select scheduling");
        List<ShortMessageServiceEntity> allByStatusIsWaitAndUpdate = shortMessageService.findAllByStatusIsWaitAndUpdate();
        if (allByStatusIsWaitAndUpdate != null && !allByStatusIsWaitAndUpdate.isEmpty()) {
            allByStatusIsWaitAndUpdate.forEach(sms -> log.info("{}", sms));
        }
        allByStatusIsWaitAndUpdate.stream()
                .map(entity -> new ShortMessage(entity.getReceiveNumber(), entity.getStatus(),entity.getSenderNumber(), entity.getMessage(), entity.getTitle(), entity.getTo()
                ,entity.get))
                .toList()
                .forEach(messageInputPort::send);
    }
}
