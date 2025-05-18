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
 *
 * Hexagonal Architecture에서 "Input Adapter" 역할
 */
@Slf4j // 로깅을 위한 Lombok 어노테이션 (@Slf4j = log.info(), log.error() 쉽게 사용 가능)
@EnableScheduling // 스프링 스케줄링 기능 활성화 (주기적 작업을 돌리겠다는 뜻)
@Component // Spring Bean으로 등록 (컴포넌트 스캔 대상)
public class SelectSchedulerInputAdapter {

    // 의존성 주입 받는 필드
    private final ShortMessageService shortMessageService; // DB 조회를 담당하는 서비스 (Status가 'wait'인 메시지를 가져옴)
    private final MessageInputPort messageInputPort; // Core 영역으로 메시지를 전달하는 포트

    // 생성자 주입 방식으로 의존성 주입
    public SelectSchedulerInputAdapter(ShortMessageService shortMessageService, MessageInputPort messageInputPort) {
        this.shortMessageService = shortMessageService;
        this.messageInputPort = messageInputPort;
    }

    /**
     * 1초 마다 실행되는 메소드.
     * <p>
     * 1초마다 대기 상태의 모든 메시지를 조회한 후 대상 데이터를 transport input port의 send 메소드를 통해 데이터를 전달한다.
     */
    @Scheduled(fixedDelay = 1000L) // 1000ms (1초) 간격으로 반복 실행
    public void select() {
        log.info("select scheduling");
        // ShortMessageService를 통해 "대기중(wait)" 상태 메시지를 조회하고, 업데이트 처리
        List<ShortMessageServiceEntity> allByStatusIsWaitAndUpdate = shortMessageService.findAllByStatusIsWaitAndUpdate();
        // 조회 결과가 비어있지 않은 경우
        if (allByStatusIsWaitAndUpdate != null && !allByStatusIsWaitAndUpdate.isEmpty()) {
            // 조회된 모든 메시지 정보를 로그로 출력
            allByStatusIsWaitAndUpdate.forEach(sms -> log.info("{}", sms));
        }
        // 조회된 메시지들을
        allByStatusIsWaitAndUpdate.stream()
                // Entity 객체를 Domain 객체(ShortMessage)로 변환
                .map(entity -> new ShortMessage(
                        entity.getId(),
                        entity.getReceiveNumber(),
                        entity.getCallbackNumber(),
                        entity.getMessage(),
                        entity.getStatus(),
                        entity.getTitle()))
                .toList()
                // 변환된 ShortMessage를 Core 영역으로 전송
                .forEach(messageInputPort::send);
    }
}
