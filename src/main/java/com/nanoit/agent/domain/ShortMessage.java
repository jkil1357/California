package com.nanoit.agent.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class ShortMessage implements Message {

    private String senderNumber;   //발신자
    private String receiverNumber; //수신자
    private String title;          //메세지 제목
    private String content;        //메세지 내용
    private String status;
    private String to;          // 전송 대상: KT, NANOIT

    private int priority;
    private boolean urgent;

    public ShortMessage(String senderNumber, String receiverNumber, String title, String content, String status, String to, int priority, boolean urgent) {
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
        this.title = title;
        this.content = content;
        this.status = status;
        this.to = to;
        this.priority = priority;
        this.urgent = urgent;
    }

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
