package ru.alishev.springcourse.FirstJWTApp.dto;

public class PersonErrorResponse {
    private String errorMessage;
    private long timestamp;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
