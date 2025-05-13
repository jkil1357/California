package com.nanoit.agent.domain;

public interface Message {

    
    String id();              
    String receiveNumber();     
    String callbackNumber();   
    String message();           

    // 분기 처리와 상태 저장을 위한 필수 메서드
    String to();                              // KT, NANOIT
    void setStatus(MessageStatus status);     // OK, FAIL
    MessageStatus getStatus();                // 상태 조회
}
