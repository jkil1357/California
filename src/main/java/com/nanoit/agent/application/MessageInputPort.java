package com.nanoit.agent.application;

public interface MessageInputPort {
    void sendSms(String phoneNumber, String message);
}
