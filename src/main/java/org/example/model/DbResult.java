package org.example.model;

import java.time.LocalDateTime;

public class DbResult {

    private LocalDateTime timestamp;
    private double dbConnectTimeMs;
    private double queryTimeMs;
    private boolean success;
    private String errorMessage;

    public DbResult() {
    }

    public DbResult(LocalDateTime timestamp,
                    double dbConnectTimeMs,
                    double queryTimeMs,
                    boolean success,
                    String errorMessage) {
        this.timestamp = timestamp;
        this.dbConnectTimeMs = dbConnectTimeMs;
        this.queryTimeMs = queryTimeMs;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getDbConnectTimeMs() {
        return dbConnectTimeMs;
    }

    public void setDbConnectTimeMs(double dbConnectTimeMs) {
        this.dbConnectTimeMs = dbConnectTimeMs;
    }

    public double getQueryTimeMs() {
        return queryTimeMs;
    }

    public void setQueryTimeMs(double queryTimeMs) {
        this.queryTimeMs = queryTimeMs;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("DbResult{")
                .append("timestamp=").append(timestamp)
                .append(", dbConnectTimeMs=").append(dbConnectTimeMs)
                .append(", queryTimeMs=").append(queryTimeMs)
                .append(", success=").append(success);

        if (!success && errorMessage != null) {
            builder.append(", errorMessage='")
                    .append(errorMessage)
                    .append('\'');
        }

        builder.append('}');

        return builder.toString();
    }
}