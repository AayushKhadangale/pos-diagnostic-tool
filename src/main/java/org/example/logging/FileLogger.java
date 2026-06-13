package org.example.logging;

import org.example.model.AnomalyEvent;
import org.example.model.DbResult;
import org.example.model.ProbeResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    private final String logFilePath;

    public FileLogger(String logDirectory) {

        File directory = new File(logDirectory);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        this.logFilePath =
                logDirectory +
                        File.separator +
                        "diagnostic_" +
                        timestamp +
                        ".log";
    }

    public void logNetworkProbe(ProbeResult result) {

        writeLine(
                "[NETWORK] " +
                        result.toString()
        );
    }

    public void logDatabaseProbe(DbResult result) {

        writeLine(
                "[DATABASE] " +
                        result.toString()
        );
    }

    public void logAnomaly(AnomalyEvent anomalyEvent) {

        writeLine(
                "[ANOMALY] " +
                        anomalyEvent.toString()
        );
    }

    public void logSummary(String summary) {

        writeLine("");
        writeLine("===== FINAL SUMMARY =====");
        writeLine(summary);
    }

    private void writeLine(String message) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(logFilePath, true))) {

            writer.write(message);
            writer.newLine();

        } catch (IOException e) {

            System.err.println(
                    "Failed to write log: "
                            + e.getMessage()
            );
        }
    }

    public String getLogFilePath() {
        return logFilePath;
    }
}