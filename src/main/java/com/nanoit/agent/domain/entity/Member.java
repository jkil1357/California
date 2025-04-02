package com.nanoit.agent.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자가 필요
@Entity // JPA 엔티티임을 선언
@Table(name = "member") // 데이터베이스 테이블 이름 지정
public class Member {

    @Id // 기본 키(Primary Key) 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스가 ID 자동 생성 (Auto-increment)
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // null 불가, 유니크 제약조건, 길이 50
    private String loginId; // 사용자 로그인 아이디

    @Column(nullable = false) // null 불가
    private String password; // 암호화된 비밀번호 저장 필드

    @Column(nullable = false, length = 100) // null 불가, 길이 100
    private String name; // 사용자 이름

    @Column(length = 100) // null 허용 (선택 사항)
    private String email; // 사용자 이메일

    // 빌더 패턴으로 객체 생성 (생성 시점에 비밀번호 암호화 필요)
    @Builder
    public Member(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password; // 서비스 레이어에서 암호화된 비밀번호를 받아야 함
        this.name = name;
        this.email = email;
    }
}