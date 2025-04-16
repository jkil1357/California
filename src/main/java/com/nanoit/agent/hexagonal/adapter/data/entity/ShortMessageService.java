package com.nanoit.domain.message.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sms_msg")
public class ShortMessageServiceEntity {

    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36, nullable = false)
    private String id;x`

    @Column(name = "receive_number", length = 20, nullable = false)
    private String receiveNumber; // 수신자 전화번호

    @Column(name = "callback_number", length = 20, nullable = false)
    private String callbackNumber; // 발신자 전화번호

    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message; // 메시지 내용

    @Column(name = "receiver_name", length = 50)
    private String receiverName; // 수신자 이름 (선택)

    @Column(name = "channel", length = 20, nullable = false)
    private String channel; // 메시지 발송 채널 (예: KT, NANOIT)

    @Column(name = "message_type", length = 20, nullable = false)
    private String messageType; // 메시지 타입 (예: SMS, LMS, EMAIL)

    @Column(name = "status", length = 8, nullable = false)
    private String status; // SUCCESS / FAIL / PENDING

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage; // 실패 시 에러 메시지 (nullable)

    @Column(name = "created_datetime", nullable = false)
    @ColumnDefault("now()")
    private LocalDateTime createdDatetime; // 생성 시각

    @Column(name = "modified_datetime", nullable = false)
    @ColumnDefault("now()")
    private LocalDateTime modifiedDatetime; // 수정 시각

    /**
     * 메시지 ID 자동 생성
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
