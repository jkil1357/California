package com.nanoit.agent.application;


import com.nanoit.agent.domain.ShortMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageUseCase messageUseCase;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody MessageDTO.MessageRequestDto dto) {
        ShortMessage message = ShortMessage.builder()
                .senderNumber(dto.getSenderNumber())
                .receiverNumber(dto.getReceiverNumber())
                .title(dto.getTitle())
                .content(dto.getContent())
                .status("READY")
                .to(dto.getTo())
                .build();

        messageUseCase.send(message);
        return ResponseEntity.ok("메시지 전송 완료");
    }
}



