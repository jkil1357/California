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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "sms_msg")
public class ShortMessageServiceEntity {

    @Id
    @Column(name = "id", length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String id;

    @Column(name = "receive_number", length = 20)
    private String receiveNumber;

    @Column(name = "callback_number", length = 20)
    private String callbackNumber;

    @Column(name = "message", length = 70)
    private String message;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "result", length = 256)
    private String result;

    @ColumnDefault("now()")
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDateTime;

    @ColumnDefault("now()")
    @Column(name = "modified_datetime", nullable = false)
    private LocalDateTime modifiedDateTime;
}
