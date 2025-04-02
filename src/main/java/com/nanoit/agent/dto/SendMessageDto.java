package com.nanoit.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageDto {
    private String recipientNumber;
    private String content;
}