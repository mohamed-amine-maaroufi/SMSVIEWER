package com.amine.smsviewer;

public class Message {
    private String messageNumber, messageContent, date;
    private float fee;
    private int id;

    public Message(String messageNumber, String messageContent, String date, float fee) {

        this.messageNumber = messageNumber;
        this.messageContent = messageContent;
        this.date = date;
        this.fee = fee;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}