package com.nanoit.agent.hexagonal.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequestDto {
    private String phoneNumber;
    private String message;
}
