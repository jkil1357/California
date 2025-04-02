package com.nanoit.agent.hexagonal.adapter.data;

import com.nanoit.agent.hexagonal.adapter.data.entity.ShortMessageService;
import com.nanoit.agent.hexagonal.adapter.data.service.ShortMessageServiceService;
import com.nanoit.agent.hexagonal.application.MessageInputPort;
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
public class SelectScheduler {

    private final ShortMessageServiceService shortMessageServiceService;
    private final MessageInputPort messageInputPort;

    public SelectScheduler(ShortMessageServiceService shortMessageServiceService, MessageInputPort messageInputPort) {
        this.shortMessageServiceService = shortMessageServiceService;
        this.messageInputPort = messageInputPort;
    }

    /**
     * 1초 마다 실행되는 메소드.
     *
     * 1초마다 대기 상태의 모든 메시지를 조회한 후 대상 데이터를 transport input port의 send 메소드를 통해 데이터를 전달한다.
     */
    @Scheduled(fixedDelay = 1000L)
    public void select() {
        log.info("select scheduling");
        List<ShortMessageService> allByStatusIsWaitAndUpdate = shortMessageServiceService.findAllByStatusIsWaitAndUpdate();
        if (allByStatusIsWaitAndUpdate != null && !allByStatusIsWaitAndUpdate.isEmpty()) {
            allByStatusIsWaitAndUpdate.forEach(sms -> log.info("{}", sms));
        }
        messageInputPort.send(null);
    }
}
