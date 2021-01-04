package org.nebezdari.validators;

public class Message {

    private Status status;
    private String message;

    public Message(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Message(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
