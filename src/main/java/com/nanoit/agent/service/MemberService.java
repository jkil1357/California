package com.nanoit.agent.service;

import com.nanoit.agent.dto.MemberRegisterDto;
import com.nanoit.agent.dto.SendMessageDto;

public interface MemberService {
    /**
     * 회원 가입 처리
     * @param memberRegisterDto 회원 가입 정보 DTO
     * @throws Exception 아이디 중복 등 예외 발생 가능
     */
    void register(MemberRegisterDto memberRegisterDto) throws Exception;

    /**
     * 로그인 인증 처리
     * @param loginId 로그인 아이디
     * @param rawPassword 입력된 비밀번호 (암호화되지 않은 상태)
     * @return 인증 성공 여부
     */
    boolean authenticate(String loginId, String rawPassword);

    /**
     * 사용자가 보낸 메시지를 저장합니다.
     * @param sendMessageDto 메시지 내용 DTO
     * @param senderLoginId 메시지를 보낸 사용자의 로그인 ID
     * @throws Exception 사용자 조회 실패 등 예외 발생 가능
     */
    void saveMessage(SendMessageDto sendMessageDto, String senderLoginId) throws Exception;
}