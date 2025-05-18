package com.nanoit.agent.domain;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ShortMessage(
        String id,
        String receiveNumber,
        String callbackNumber,
        String message,
        String status,
        String to
) implements Message {
}
