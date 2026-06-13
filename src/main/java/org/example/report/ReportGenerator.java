package org.example.report;

import org.example.model.AnomalyEvent;
import org.example.model.DbResult;
import org.example.model.ProbeResult;

import java.util.List;

public class ReportGenerator {

    public String generateReport(
            List<ProbeResult> probeResults,
            List<DbResult> dbResults,
            List<AnomalyEvent> anomalies) {

        int totalSamples = probeResults.size();

        double minLatency = Double.MAX_VALUE;
        double maxLatency = Double.MIN_VALUE;

        double latencySum = 0.0;
        double jitterSum = 0.0;

        int failedProbes = 0;

        for (int i = 0; i < probeResults.size(); i++) {

            ProbeResult result = probeResults.get(i);

            if (!result.isSuccess()) {
                failedProbes++;
                continue;
            }

            double latency = result.getLatencyMs();

            minLatency = Math.min(minLatency, latency);
            maxLatency = Math.max(maxLatency, latency);

            latencySum += latency;

            if (i > 0) {

                ProbeResult previous = probeResults.get(i - 1);

                if (previous.isSuccess()) {

                    jitterSum += Math.abs(
                            latency - previous.getLatencyMs()
                    );
                }
            }
        }

        double averageLatency =
                totalSamples > 0
                        ? latencySum / totalSamples
                        : 0.0;

        double jitter =
                totalSamples > 1
                        ? jitterSum / (totalSamples - 1)
                        : 0.0;

        double packetLossPercentage =
                totalSamples > 0
                        ? ((double) failedProbes / totalSamples) * 100
                        : 0.0;

        double dbConnectSum = 0.0;
        double dbQuerySum = 0.0;

        int successfulDbSamples = 0;

        for (DbResult dbResult : dbResults) {

            if (dbResult.isSuccess()) {

                dbConnectSum += dbResult.getDbConnectTimeMs();
                dbQuerySum += dbResult.getQueryTimeMs();

                successfulDbSamples++;
            }
        }

        double averageDbConnectTime =
                successfulDbSamples > 0
                        ? dbConnectSum / successfulDbSamples
                        : 0.0;

        double averageDbQueryTime =
                successfulDbSamples > 0
                        ? dbQuerySum / successfulDbSamples
                        : 0.0;

        int anomalyCount = anomalies.size();

        String verdict = generateVerdict(
                packetLossPercentage,
                averageLatency,
                averageDbQueryTime
        );

        StringBuilder report = new StringBuilder();

        report.append(System.lineSeparator());
        report.append("===== DIAGNOSTIC SUMMARY =====")
                .append(System.lineSeparator());

        report.append("Total Samples: ")
                .append(totalSamples)
                .append(System.lineSeparator());

        report.append("Min Latency (ms): ")
                .append(String.format("%.2f", minLatency))
                .append(System.lineSeparator());

        report.append("Max Latency (ms): ")
                .append(String.format("%.2f", maxLatency))
                .append(System.lineSeparator());

        report.append("Average Latency (ms): ")
                .append(String.format("%.2f", averageLatency))
                .append(System.lineSeparator());

        report.append("Jitter (ms): ")
                .append(String.format("%.2f", jitter))
                .append(System.lineSeparator());

        report.append("Packet Loss (%): ")
                .append(String.format("%.2f", packetLossPercentage))
                .append(System.lineSeparator());

        report.append("Average DB Connect Time (ms): ")
                .append(String.format("%.2f", averageDbConnectTime))
                .append(System.lineSeparator());

        report.append("Average DB Query Time (ms): ")
                .append(String.format("%.2f", averageDbQueryTime))
                .append(System.lineSeparator());

        report.append("Anomaly Count: ")
                .append(anomalyCount)
                .append(System.lineSeparator());

        report.append("Verdict: ")
                .append(verdict)
                .append(System.lineSeparator());

        return report.toString();
    }

    private String generateVerdict(
            double packetLoss,
            double averageLatency,
            double averageDbQueryTime) {

        if (packetLoss > 5.0) {
            return "Network instability detected due to packet loss.";
        }

        if (averageLatency > 100.0) {
            return "High network latency detected.";
        }

        if (averageDbQueryTime > 100.0) {
            return "Database performance bottleneck detected.";
        }

        return "System operating normally.";
    }
}