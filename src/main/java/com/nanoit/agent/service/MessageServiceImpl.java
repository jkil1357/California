package com.nanoit.agent.service;

import com.nanoit.agent.domain.entity.Member;
import com.nanoit.agent.domain.entity.UserMessage;
import com.nanoit.agent.domain.repository.MemberRepository;
import com.nanoit.agent.domain.repository.UserMessageRepository;
import com.nanoit.agent.dto.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserMessageRepository userMessageRepository;
    private final MemberRepository memberRepository; // 사용자 정보 조회를 위해 MemberRepository 주입

    @Override
    @Transactional
    public void saveMessage(SendMessageDto sendMessageDto, String senderLoginId) throws Exception {
        // 1. 메시지를 보낸 사용자 정보 조회
        Member sender = memberRepository.findByLoginId(senderLoginId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 보낸 사용자 정보를 찾을 수 없습니다: " + senderLoginId));

        // 2. UserMessage 엔티티 생성
        UserMessage message = UserMessage.builder()
                .recipientNumber(sendMessageDto.getRecipientNumber())
                .content(sendMessageDto.getContent())
                .sender(sender) // 조회한 Member 엔티티를 설정
                .build();

        // 3. 메시지 저장
        userMessageRepository.save(message);
        System.out.println("메시지 저장 완료: " + senderLoginId + " -> " + sendMessageDto.getRecipientNumber());
    }
}