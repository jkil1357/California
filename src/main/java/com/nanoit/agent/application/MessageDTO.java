package com.nanoit.agent.application;

public class MessageDTO
{
    class MessageRequestDto {
        private String senderNumber;
        private String receiverNumber;
        private String title;
        private String content;
        private String to;

        public String getSenderNumber() { return senderNumber; }
        public String getReceiverNumber() { return receiverNumber; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public String getTo() { return to; }
    }
}
