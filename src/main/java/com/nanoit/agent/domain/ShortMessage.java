package com.nanoit.agent.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class ShortMessage implements Message {

    private String senderNumber;
    private String receiverNumber;
    private String title;
    private String content;

    private String status;      // 전송 상태: OK, FAIL, INVALID 등
    private String to;          // 전송 대상: KT, NANOIT
    private int priority;       // 우선순위
    private boolean urgent;     // 긴급 여부

    @Override
    public String getSenderNumber() {
        return senderNumber;
    }

    @Override
    public String getReceiverNumber() {
        return receiverNumber;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    public ShortMessage withStatus(String newStatus) {
        return this.withStatus(newStatus);
    }

    public String to() {
        return this.to;
    }

    public int getPriority() {
        return this.priority;
    }

    public Boolean isUrgent() {
        return this.urgent;
    }
}
