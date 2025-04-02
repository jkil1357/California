package com.nanoit.agent.hexagonal.domain;

public interface Message {

    String getId();

    String getToPhoneNumber();

    String getFromPhoneNumber();

    String getSubject();

    String getContent();
}
