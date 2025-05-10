package com.nanoit.agent.application;

import com.nanoit.agent.application.MessageUseCase;
import com.nanoit.agent.domain.Message;
import com.nanoit.agent.domain.ShortMessage;
import com.nanoit.agent.application.KtTransportOutputPort;
import com.nanoit.agent.application.NanoitTransportOutputPort;
import com.nanoit.agent.application.PersistenceOutputPort;

public abstract class MessageInputPort {

    public static void main(String[] args) {

        KtTransportOutputPort ktPort = message -> true;
        NanoitTransportOutputPort nanoitPort = message -> false;
        PersistenceOutputPort persistencePort = message -> System.out.println("DB 저장: " + message);


        MessageUseCase messageUseCase = new MessageUseCase(ktPort, nanoitPort, persistencePort);


        ShortMessage message = ShortMessage.builder()
                .title("msg001")
                .receiverNumber("010-1234-5678")
                .senderNumber("010-8765-4321")
                .content("테스트입니다")
                .status("READY")
                .to("KT")
                .build();


        messageUseCase.send(message);
    }

    public abstract void send(Message message);
}
