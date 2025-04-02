package com.nanoit.agent.hexagonal.adapter.data.entity;

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
public class ShortMessageService {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String id;

    /**
     * 필요한 컬럼을 설계한다.
     */

    @ColumnDefault("now()")
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDateTime;

    @ColumnDefault("now()")
    @Column(name = "modified_datetime", nullable = false)
    private LocalDateTime modifiedDateTime;
}
