package com.nanoit.agent.hexagonal.transport;

import lombok.Data;

@Data
public class SmsRequestDto {
    private String receiveNumber;
    private String message;
}