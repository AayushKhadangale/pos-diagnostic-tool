class ReportGenerator {

    generateReport(
        probeResults,
        dbResults,
        anomalies,
        aiRootCause,
        operatorSummary

    ) {

        const totalSamples = probeResults.length;

        let minLatency = Number.MAX_VALUE;
        let maxLatency = Number.MIN_VALUE;

        let latencySum = 0;
        let jitterSum = 0;

        let failedProbes = 0;

        for (let i = 0; i < probeResults.length; i++) {

            const result = probeResults[i];

            if (!result.success) {
                failedProbes++;
                continue;
            }

            const latency = result.latencyMs;

            minLatency = Math.min(
                minLatency,
                latency
            );

            maxLatency = Math.max(
                maxLatency,
                latency
            );

            latencySum += latency;

            if (i > 0) {

                const previous =
                    probeResults[i - 1];

                if (previous.success) {

                    jitterSum += Math.abs(
                        latency -
                        previous.latencyMs
                    );
                }
            }
        }

        const averageLatency =
            totalSamples > 0
                ? latencySum / totalSamples
                : 0;

        const jitter =
            totalSamples > 1
                ? jitterSum /
                  (totalSamples - 1)
                : 0;

        const packetLossPercentage =
            totalSamples > 0
                ? (
                    failedProbes /
                    totalSamples
                  ) * 100
                : 0;

        let dbConnectSum = 0;
        let dbQuerySum = 0;

        let successfulDbSamples = 0;

        for (const dbResult of dbResults) {

            if (dbResult.success) {

                dbConnectSum +=
                    dbResult.dbConnectTimeMs;

                dbQuerySum +=
                    dbResult.queryTimeMs;

                successfulDbSamples++;
            }
        }

        const averageDbConnectTime =
            successfulDbSamples > 0
                ? dbConnectSum /
                  successfulDbSamples
                : 0;

        const averageDbQueryTime =
            successfulDbSamples > 0
                ? dbQuerySum /
                  successfulDbSamples
                : 0;

        const anomalyCount =
            anomalies.length;

        const verdict =
            this.generateVerdict(
                packetLossPercentage,
                averageLatency,
                averageDbQueryTime
            );

return `
===== DIAGNOSTIC SUMMARY =====

Total Samples: ${totalSamples}

Min Latency (ms): ${minLatency.toFixed(2)}
Max Latency (ms): ${maxLatency.toFixed(2)}

Average Latency (ms): ${averageLatency.toFixed(2)}
Jitter (ms): ${jitter.toFixed(2)}

Packet Loss (%): ${packetLossPercentage.toFixed(2)}

Average DB Connect Time (ms): ${averageDbConnectTime.toFixed(2)}
Average DB Query Time (ms): ${averageDbQueryTime.toFixed(2)}

Anomaly Count: ${anomalyCount}

Verdict: ${verdict}

===== AI ROOT CAUSE ANALYSIS =====

${aiRootCause}

===== OPERATOR SUMMARY =====

${operatorSummary}

`;
    }

    generateVerdict(
        packetLoss,
        averageLatency,
        averageDbQueryTime
    ) {

        if (packetLoss > 5.0) {
            return 'Network instability detected due to packet loss.';
        }

        if (averageLatency > 100.0) {
            return 'High network latency detected.';
        }

        if (averageDbQueryTime > 100.0) {
            return 'Database performance bottleneck detected.';
        }

        return 'System operating normally.';
    }
}

export default ReportGenerator;