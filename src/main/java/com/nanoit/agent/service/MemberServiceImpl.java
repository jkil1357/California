package com.nanoit.agent.service;

import com.nanoit.agent.domain.entity.Member;
import com.nanoit.agent.domain.entity.UserMessage;
import com.nanoit.agent.domain.repository.MemberRepository;
import com.nanoit.agent.domain.repository.UserMessageRepository;
import com.nanoit.agent.dto.MemberRegisterDto;
import com.nanoit.agent.dto.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 처리

import java.util.Optional;

@Service // 스프링 서비스 빈으로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성 (Lombok)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; // 리포지토리 주입
    private final PasswordEncoder passwordEncoder;   // PasswordEncoder 주입
    private final UserMessageRepository userMessageRepository;
    @Override
    @Transactional // 데이터 변경 작업이므로 트랜잭션 적용
    public void register(MemberRegisterDto memberRegisterDto) throws Exception {
        // 1. 아이디 중복 확인
        if (memberRepository.existsByLoginId(memberRegisterDto.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다."); // 예외 발생
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberRegisterDto.getPassword());

        // 3. Member 엔티티 생성 (암호화된 비밀번호 사용)
        Member member = Member.builder()
                .loginId(memberRegisterDto.getLoginId())
                .password(encodedPassword)
                .name(memberRegisterDto.getName())
                .email(memberRegisterDto.getEmail())
                .build();

        // 4. 데이터베이스에 저장
        memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true) // 데이터 조회 작업이므로 읽기 전용 트랜잭션
    public boolean authenticate(String loginId, String rawPassword) {
        // 1. 아이디로 회원 정보 조회
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            // 2. 입력된 비밀번호와 DB에 저장된 암호화된 비밀번호 비교
            return passwordEncoder.matches(rawPassword, member.getPassword());
        }

        // 3. 해당 아이디의 회원이 없으면 인증 실패
        return false;
    }

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