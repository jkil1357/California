package com.nanoit.agent.domain.repository;

import com.nanoit.agent.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 스프링 빈으로 등록
public interface MemberRepository extends JpaRepository<Member, Long> { // <엔티티 클래스, ID 타입>

    // 로그인 아이디로 회원을 찾는 메소드 (쿼리 자동 생성)
    Optional<Member> findByLoginId(String loginId);

    // 특정 로그인 아이디가 존재하는지 확인하는 메소드 (쿼리 자동 생성)
    boolean existsByLoginId(String loginId);
}