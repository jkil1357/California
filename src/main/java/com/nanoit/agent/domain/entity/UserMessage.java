package com.nanoit.agent.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; // 생성 시간 자동 기록

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_message") // 테이블 이름 지정
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String recipientNumber; // 받는 사람 번호

    @Lob // 긴 텍스트를 위한 설정
    @Column(nullable = false)
    private String content; // 메시지 내용

    @CreationTimestamp // 엔티티 생성 시 자동으로 현재 시간 저장
    @Column(nullable = false, updatable = false)
    private LocalDateTime sentTimestamp; // 보낸 시간

    // 메시지를 보낸 사람 (Member 엔티티와 관계 설정)
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "sender_id", nullable = false) // 외래 키 컬럼 지정
    private Member sender; // Member 엔티티 참조

    @Builder
    public UserMessage(String recipientNumber, String content, Member sender) {
        this.recipientNumber = recipientNumber;
        this.content = content;
        this.sender = sender;
    }
}