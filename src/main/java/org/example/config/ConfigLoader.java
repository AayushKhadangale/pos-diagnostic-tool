package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;

    public ConfigLoader() {
        properties = new Properties();

        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream("application.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("application.properties file not found");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public String getNetworkHost() {
        return properties.getProperty("network.host");
    }

    public int getNetworkPort() {
        return Integer.parseInt(properties.getProperty("network.port"));
    }

    public int getNetworkTimeoutMs() {
        return Integer.parseInt(properties.getProperty("network.timeout.ms"));
    }

    public long getProbeIntervalMs() {
        return Long.parseLong(properties.getProperty("probe.interval.ms"));
    }

    public int getProbeDurationMinutes() {
        return Integer.parseInt(properties.getProperty("probe.duration.minutes"));
    }

    public double getLatencyThresholdMs() {
        return Double.parseDouble(properties.getProperty("latency.threshold.ms"));
    }

    public double getDbConnectThresholdMs() {
        return Double.parseDouble(properties.getProperty("db.connect.threshold.ms"));
    }

    public double getDbQueryThresholdMs() {
        return Double.parseDouble(properties.getProperty("db.query.threshold.ms"));
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getLogDirectory() {
        return properties.getProperty("log.directory");
    }
}