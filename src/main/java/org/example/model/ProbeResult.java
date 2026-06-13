package org.example.model;

import java.time.LocalDateTime;

public class ProbeResult {

    private LocalDateTime timestamp;
    private double latencyMs;
    private boolean reachable;
    private boolean success;

    public ProbeResult() {
    }

    public ProbeResult(LocalDateTime timestamp,
                       double latencyMs,
                       boolean reachable,
                       boolean success) {
        this.timestamp = timestamp;
        this.latencyMs = latencyMs;
        this.reachable = reachable;
        this.success = success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatencyMs() {
        return latencyMs;
    }

    public void setLatencyMs(double latencyMs) {
        this.latencyMs = latencyMs;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ProbeResult{" +
                "timestamp=" + timestamp +
                ", latencyMs=" + latencyMs +
                ", reachable=" + reachable +
                ", success=" + success +
                '}';
    }
}