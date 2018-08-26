package com.example.slavick.firebasechat;

import java.util.Date;

public class Message {
    private String messageUser;
    private String messageText;
    private long messageTime;

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {

        return messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public  Message() {}

    public Message(String messageUser, String messageText) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        messageTime = new Date().getTime();

    }
}
