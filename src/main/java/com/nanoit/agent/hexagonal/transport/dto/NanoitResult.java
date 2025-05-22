package com.nanoit.agent.hexagonal.transport.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record NanoitResult(
        String code,
        String message,





        String recvId,      // 메시지 ID → DB id
        String status,      // "OK" / "FAIL"
        String recvNumber,  // 수신자
        String callback
        // ..
        ) {
}
