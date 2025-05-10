package com.nanoit.agent.hexagonal.data.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sms_msg")
public class ShortMessageServiceEntity {

    @Id
    @Column(name = "title", nullable = false, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String title;

    @Column(name = "Receive_number", nullable = false, length = 20)
    private String ReceiveNumber;

    @Column(name = "SenderNumber", nullable = false, length = 20)
    private String SenderNumber;

    @Column(name = "message", nullable = false, length = 20)
    private String message;

    @Column(name = "to", nullable = false, length = 20)
    private String to;

    @Column(name = "status", nullable = false, length = 8)
    private String status;

    @ColumnDefault("now()")
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDateTime;

    @ColumnDefault("now()")
    @Column(name = "modified_datetime", nullable = false)
    private LocalDateTime modifiedDateTime;
}
