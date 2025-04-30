package com.nanoit.agent.domain;

public interface Message {

    String getSenderNumber();    // 발신번호
    String getReceiverNumber(); // 수신번호
    String getTitle();          // 제목
    String getContent();        // 메시지 내용
}
