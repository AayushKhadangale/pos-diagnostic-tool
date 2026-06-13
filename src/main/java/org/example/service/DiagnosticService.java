package org.example.service;

import org.example.config.ConfigLoader;
import org.example.database.DatabaseProbe;
import org.example.logging.FileLogger;
import org.example.model.AnomalyEvent;
import org.example.model.DbResult;
import org.example.model.ProbeResult;
import org.example.network.NetworkProbe;
import org.example.report.ReportGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticService {

    private final ConfigLoader configLoader;

    private final NetworkProbe networkProbe;
    private final DatabaseProbe databaseProbe;

    private final FileLogger fileLogger;
    private final ReportGenerator reportGenerator;

    private final List<ProbeResult> probeResults;
    private final List<DbResult> dbResults;
    private final List<AnomalyEvent> anomalyEvents;

    public DiagnosticService() {

        this.configLoader = new ConfigLoader();

        this.networkProbe = new NetworkProbe(
                configLoader.getNetworkHost(),
                configLoader.getNetworkPort(),
                configLoader.getNetworkTimeoutMs()
        );

        this.databaseProbe = new DatabaseProbe(
                configLoader.getDbUrl(),
                configLoader.getDbUsername(),
                configLoader.getDbPassword()
        );

        this.fileLogger = new FileLogger(configLoader.getLogDirectory());

        this.reportGenerator = new ReportGenerator();

        this.probeResults = new ArrayList<>();
        this.dbResults = new ArrayList<>();
        this.anomalyEvents = new ArrayList<>();
    }

    public void startDiagnostic() {

        long intervalMs =
                configLoader.getProbeIntervalMs();

        long durationMs =
                configLoader.getProbeDurationMinutes()
                        * 60L
                        * 1000L;

        long startTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startTime)
                < durationMs) {

            ProbeResult probeResult = networkProbe.executeProbe();

            probeResults.add(probeResult); //result of networkprobe

            fileLogger.logNetworkProbe(probeResult);

            DbResult dbResult = databaseProbe.executeProbe();

            dbResults.add(dbResult);

            fileLogger.logDatabaseProbe(dbResult);

            detectNetworkAnomaly(probeResult);

            detectDatabaseAnomaly(dbResult);

            printCurrentSample(
                    probeResult,
                    dbResult
            );

            try {

                Thread.sleep(intervalMs);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.err.println(
                        "Diagnostic interrupted."
                );

                break;
            }
        }

        generateFinalReport();
    }

    private void detectNetworkAnomaly(
            ProbeResult probeResult) {

        if (probeResult.isSuccess()
                && probeResult.getLatencyMs()
                > configLoader.getLatencyThresholdMs()) {

            AnomalyEvent anomaly =
                    new AnomalyEvent(
                            LocalDateTime.now(),
                            "NETWORK",
                            "Network latency exceeded threshold",
                            probeResult.getLatencyMs(),
                            configLoader.getLatencyThresholdMs()
                    );

            anomalyEvents.add(anomaly);

            fileLogger.logAnomaly(anomaly);
        }
    }

    private void detectDatabaseAnomaly(
            DbResult dbResult) {

        if (!dbResult.isSuccess()) {
            return;
        }

        if (dbResult.getDbConnectTimeMs()
                > configLoader.getDbConnectThresholdMs()) {

            AnomalyEvent anomaly =
                    new AnomalyEvent(
                            LocalDateTime.now(),
                            "DATABASE",
                            "Database connection time exceeded threshold",
                            dbResult.getDbConnectTimeMs(),
                            configLoader.getDbConnectThresholdMs()
                    );

            anomalyEvents.add(anomaly);

            fileLogger.logAnomaly(anomaly);
        }

        if (dbResult.getQueryTimeMs()
                > configLoader.getDbQueryThresholdMs()) {

            AnomalyEvent anomaly =
                    new AnomalyEvent(
                            LocalDateTime.now(),
                            "DATABASE",
                            "Database query time exceeded threshold",
                            dbResult.getQueryTimeMs(),
                            configLoader.getDbQueryThresholdMs()
                    );

            anomalyEvents.add(anomaly);

            fileLogger.logAnomaly(anomaly);
        }
    }

    private void printCurrentSample(
            ProbeResult probeResult,
            DbResult dbResult) {

        System.out.println(
                "Network => " + probeResult
        );

        System.out.println(
                "Database => " + dbResult
        );

        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------"
        );
    }

    private void generateFinalReport() {

        String report =
                reportGenerator.generateReport(
                        probeResults,
                        dbResults,
                        anomalyEvents
                );

        System.out.println(report);

        fileLogger.logSummary(report);

        System.out.println(
                "Log File: "
                        + fileLogger.getLogFilePath()
        );
    }
}