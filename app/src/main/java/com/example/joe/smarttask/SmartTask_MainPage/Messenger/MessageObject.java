package com.example.joe.smarttask.SmartTask_MainPage.Messenger;

/**
 * Created by joe on 07/05/2017.
 */

public class MessageObject {

    private String message;
    private Long dateTime;
    private String senderName;
    private String senderId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
