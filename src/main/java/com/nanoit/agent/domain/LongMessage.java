package com.nanoit.agent.domain;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record LongMessage(
        String id,
        String receiveNumber,
        String callbackNumber,
        String message,
        String status,
        String to,

        String subject
) implements Message {
}
