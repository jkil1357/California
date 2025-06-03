package com.nanoit.agent.hexagonal.transport.dto;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder


public record NanoitSms(
        String accountType,
        String id,
        String ymd,
        String apiKey,
        String msg,
        String callNumber,
        List<RecvData> recvData
) {

    @With
    @Builder
    public record RecvData(
            String msg,
            String recvId,
            String recvNumber
    ) {

    }
}