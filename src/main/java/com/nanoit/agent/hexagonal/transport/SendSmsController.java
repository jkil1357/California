package com.nanoit.agent.hexagonal.transport;

import com.nanoit.agent.application.MessageInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SendSmsController {

    private final MessageInputPort messageInputPort;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequestDto requestDto) {
        messageInputPort.sendSms(requestDto.getPhoneNumber(), requestDto.getMessage());
        return ResponseEntity.ok("문자 전송 완료");
    }
}
