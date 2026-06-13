package org.example.model;

import java.time.LocalDateTime;

public class AnomalyEvent {

    private LocalDateTime timestamp;
    private String type;
    private String message;
    private double actualValue;
    private double thresholdValue;

    public AnomalyEvent() {
    }

    public AnomalyEvent(LocalDateTime timestamp,
                        String type,
                        String message,
                        double actualValue,
                        double thresholdValue) {
        this.timestamp = timestamp;
        this.type = type;
        this.message = message;
        this.actualValue = actualValue;
        this.thresholdValue = thresholdValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    @Override
    public String toString() {
        return "AnomalyEvent{" +
                "timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", actualValue=" + actualValue +
                ", thresholdValue=" + thresholdValue +
                '}';
    }
}