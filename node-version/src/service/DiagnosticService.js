import ConfigLoader from '../config/config.js';

import NetworkProbe from '../probes/NetworkProbe.js';
import DatabaseProbe from '../probes/DatabaseProbe.js';

import FileLogger from '../logging/FileLogger.js';
import ReportGenerator from '../report/ReportGenerator.js';
import ClaudeClient from '../ai/ClaudeClient.js';
import RootCauseAnalyzer from '../ai/RootCauseAnalyzer.js';
import NaturalLanguageReporter from '../ai/NaturalLanguageReporter.js';

import AnomalyEvent from '../model/AnomalyEvent.js';

const sleep = ms =>
    new Promise(resolve =>
        setTimeout(resolve, ms));

class DiagnosticService {

    constructor() {

        this.configLoader =
            new ConfigLoader();

        this.networkProbe =
            new NetworkProbe(
                this.configLoader.getNetworkHost(),
                this.configLoader.getNetworkPort(),
                this.configLoader.getNetworkTimeoutMs()
            );

        this.databaseProbe =
            new DatabaseProbe(
                this.configLoader.getDbHost(),
                this.configLoader.getDbPort(),
                this.configLoader.getDbName(),
                this.configLoader.getDbUser(),
                this.configLoader.getDbPassword()
            );

        this.fileLogger =
            new FileLogger(
                this.configLoader.getLogDirectory()
            );

        this.reportGenerator =
            new ReportGenerator();

            this.claudeClient =
                new ClaudeClient(
                    this.configLoader.getGeminiApiKey()
                );

            this.rootCauseAnalyzer =
                new RootCauseAnalyzer(
                    this.claudeClient
                );

            this.naturalLanguageReporter =
                new NaturalLanguageReporter(
                    this.claudeClient
                );

                this.aiRootCause = '';
                this.operatorSummary = '';

        this.probeResults = [];
        this.dbResults = [];
        this.anomalyEvents = [];
    }

    async startDiagnostic() {

        await this.fileLogger.initialize();

        const intervalMs =
            this.configLoader.getProbeIntervalMs();

        const durationMs =
            this.configLoader.getProbeDurationMinutes()
            * 60
            * 1000;

        const startTime = Date.now();

        while (
            Date.now() - startTime <
            durationMs
        ) {

            const probeResult =
                await this.networkProbe.executeProbe();

            this.probeResults.push(
                probeResult
            );

            await this.fileLogger
                .logNetworkProbe(
                    probeResult
                );

            const dbResult =
                await this.databaseProbe.executeProbe();

            this.dbResults.push(
                dbResult
            );

            await this.fileLogger
                .logDatabaseProbe(
                    dbResult
                );

            await this.detectNetworkAnomaly(
                probeResult
            );

            await this.detectDatabaseAnomaly(
                dbResult
            );

            this.printCurrentSample(
                probeResult,
                dbResult
            );

            await sleep(intervalMs);
        }

        await this.generateFinalReport();
    }

    async detectNetworkAnomaly(
        probeResult
    ) {

        if (
            probeResult.success &&
            probeResult.latencyMs >
            this.configLoader
                .getLatencyThresholdMs()
        ) {

            const anomaly =
                new AnomalyEvent(
                    new Date(),
                    'NETWORK',
                    'Network latency exceeded threshold',
                    probeResult.latencyMs,
                    this.configLoader
                        .getLatencyThresholdMs()
                );

            this.anomalyEvents.push(
                anomaly
            );

            await this.fileLogger
                .logAnomaly(anomaly);
        }
    }

    async detectDatabaseAnomaly(
        dbResult
    ) {

        if (!dbResult.success) {
            return;
        }

        if (
            dbResult.dbConnectTimeMs >
            this.configLoader
                .getDbConnectThresholdMs()
        ) {

            const anomaly =
                new AnomalyEvent(
                    new Date(),
                    'DATABASE',
                    'Database connection time exceeded threshold',
                    dbResult.dbConnectTimeMs,
                    this.configLoader
                        .getDbConnectThresholdMs()
                );

            this.anomalyEvents.push(
                anomaly);

            await this.fileLogger
                .logAnomaly(anomaly);
        }

        if (
            dbResult.queryTimeMs >
            this.configLoader
                .getDbQueryThresholdMs()
        ) {

            const anomaly =
                new AnomalyEvent(
                    new Date(),
                    'DATABASE',
                    'Database query time exceeded threshold',
                    dbResult.queryTimeMs,
                    this.configLoader
                        .getDbQueryThresholdMs()
                );

            this.anomalyEvents.push(
                anomaly
            );

            await this.fileLogger
                .logAnomaly(anomaly);
        }
    }

    printCurrentSample(
        probeResult,
        dbResult
    ) {

        console.log(
            `Network => ${probeResult.toString()}`
        );

        console.log(
            `Database => ${dbResult.toString()}`
        );

        console.log(
            '---------------------------------------------------------------------------------------------------------'
        );
    }


    async generateFinalReport() {
      await this.generateAiInsights();
        const report =
            this.reportGenerator
                .generateReport(
                    this.probeResults,
                    this.dbResults,
                    this.anomalyEvents,
                    this.aiRootCause,
                    this.operatorSummary

                );

        console.log(report);

        await this.fileLogger
            .logSummary(report);

        console.log(
            `Log File: ${this.fileLogger.getLogFilePath()}`
        );
    }


     async generateAiInsights(){
    try {

        const latestProbe =
            this.probeResults[
                this.probeResults.length - 1
            ];

        const latestDb =
            this.dbResults[
                this.dbResults.length - 1
            ];

        const aiInput = {

            latencyMs:
                latestProbe?.latencyMs,

            reachable:
                latestProbe?.success,

            dbConnectionMs:
                latestDb?.dbConnectTimeMs,

            dbQueryMs:
                latestDb?.queryTimeMs,

            dbSuccess:
                latestDb?.success,

            anomalies:
                this.anomalyEvents.map(
                    event => event.description
                )
        };

        this.aiRootCause =
            await this.rootCauseAnalyzer
                .analyze(aiInput);

        this.operatorSummary =
            await this
                .naturalLanguageReporter
                .generate(
                    this.aiRootCause
                );

    }
    catch (error) {

        console.error(
            'AI Processing Failed:',
            error.message
        );

        this.aiRootCause =
            'AI root cause analysis unavailable.';

        this.operatorSummary =
            'Operator summary unavailable.';
    }
}
}

export default DiagnosticService;