package com.nanoit.agent.hexagonal.transport.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record NanoitResult(
        String code,
        String message
        // ..
        ) {
}
