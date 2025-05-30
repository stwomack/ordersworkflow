package com.womack.ordersworkflow.domain;

public class OrderActivityOutput {
    String message = "";

    public OrderActivityOutput() {
    }

    public OrderActivityOutput(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void addMessage(String newMessage) {
        message = String.join(" -- ", message, newMessage);
    }
}

