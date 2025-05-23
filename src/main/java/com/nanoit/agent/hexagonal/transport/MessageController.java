package com.nanoit.agent.hexagonal.transport;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import com.nanoit.agent.hexagonal.data.common.repository.ShortMessageServiceRepository;

@RestController
public class MessageController {

    @Autowired
    private ShortMessageServiceRepository repository;

    @PostMapping("/send-message")
    public String sendMessage(@RequestBody Map<String, String> data) {
        try {
            String receiveNumber = data.get("receiveNumber");
            String message = data.get("message");

            System.out.println(">>> 수신 번호: " + receiveNumber);
            System.out.println(">>> 메시지: " + message);

            if (receiveNumber == null || receiveNumber.isBlank() ||
                    message == null || message.isBlank()) {
                return "수신 번호와 메시지를 모두 입력해주세요.";
            }

            ShortMessageServiceEntity entity = new ShortMessageServiceEntity();
            entity.setCreatedDateTime(LocalDateTime.now());
            entity.setModifiedDateTime(LocalDateTime.now());
            entity.setId(UUID.randomUUID().toString());
            entity.setReceiveNumber(receiveNumber);
            entity.setCallbackNumber("01012345678");
            entity.setMessage(message);
            entity.setStatus("WAIT");
            entity.setTitle("null"); // to 필드는 nullable = false라면 꼭 설정

            repository.save(entity);
            return "문자전송 성공!";
        } catch (Exception e) {
            e.printStackTrace();
            return "서버 내부 오류 발생: " + e.getMessage();
        }
    }
}