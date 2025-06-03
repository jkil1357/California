package com.nanoit.agent.hexagonal.transport.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record NanoitResponse(
        String code,
        String message
) {
}