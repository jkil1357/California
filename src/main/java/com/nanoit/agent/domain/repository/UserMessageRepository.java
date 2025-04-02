package com.nanoit.agent.domain.repository;

import com.nanoit.agent.domain.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
    // 필요한 추가적인 조회 메소드가 있다면 여기에 정의 (예: 특정 사용자가 보낸 메시지 목록 조회)
    // List<UserMessage> findBySenderOrderBySentTimestampDesc(Member sender);
}