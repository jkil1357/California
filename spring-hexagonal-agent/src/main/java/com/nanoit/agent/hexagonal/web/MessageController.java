package com.nanoit.agent.hexagonal.web;

import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import com.nanoit.agent.hexagonal.data.common.service.ShortMessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@Slf4j
public class MessageController {
    private final ShortMessageService shortMessageService;

    public MessageController(ShortMessageService shortMessageService) {
        this.shortMessageService = shortMessageService;
    }

    @PostMapping
    public ResponseEntity<ShortMessageServiceEntity> createMessage(@RequestBody MessageRequest request) {
        try {
            ShortMessageServiceEntity entity = new ShortMessageServiceEntity();
            entity.setId(UUID.randomUUID().toString());
            entity.setReceiveNumber(request.getReceiveNumber());
            entity.setCallbackNumber(request.getCallbackNumber());
            entity.setMessage(request.getMessage());
            entity.setTo(request.getTo());

            ShortMessageServiceEntity savedEntity = shortMessageService.create(entity);
            return ResponseEntity.ok(savedEntity);
        } catch (Exception e) {
            log.error("Failed to create message: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

@Data
class MessageRequest {
    private String receiveNumber;
    private String callbackNumber;
    private String message;
    private String to;
}
