package com.nanoit.agent.service;

import com.nanoit.agent.dto.SendMessageDto;

public interface MessageService {
    /**
     * 사용자가 보낸 메시지를 저장합니다.
     * @param sendMessageDto 메시지 내용 DTO
     * @param senderLoginId 메시지를 보낸 사용자의 로그인 ID
     * @throws Exception 사용자 조회 실패 등 예외 발생 가능
     */
    void saveMessage(SendMessageDto sendMessageDto, String senderLoginId) throws Exception;
}