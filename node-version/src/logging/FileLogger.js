import fs from 'fs/promises';
import path from 'path';

class FileLogger {

    constructor(logDirectory) {

        this.logDirectory = logDirectory;

        const timestamp = this.generateTimestamp();

        this.logFilePath = path.join(
            logDirectory,
            `diagnostic_${timestamp}.log`
        );
    }

    async initialize() {

        await fs.mkdir(
            this.logDirectory,
            { recursive: true }
        );
    }

    generateTimestamp() {

        const now = new Date();

        const pad = value =>
            value.toString().padStart(2, '0');

        return (
            now.getFullYear() +
            pad(now.getMonth() + 1) +
            pad(now.getDate()) +
            '_' +
            pad(now.getHours()) +
            pad(now.getMinutes()) +
            pad(now.getSeconds())
        );
    }

    async logNetworkProbe(result) {

        await this.writeLine(
            `[NETWORK] ${result.toString()}`
        );
    }

    async logDatabaseProbe(result) {

        await this.writeLine(
            `[DATABASE] ${result.toString()}`
        );
    }

    async logAnomaly(anomaly) {

        await this.writeLine(
            `[ANOMALY] ${anomaly.toString()}`
        );
    }

    async logSummary(summary) {

        await this.writeLine('');

        await this.writeLine(
            '===== FINAL SUMMARY ====='
        );

        await this.writeLine(summary);
    }

    async writeLine(message) {

        try {

            await fs.appendFile(
                this.logFilePath,
                `${message}\n`
            );

        } catch (error) {

            console.error(
                `Failed to write log: ${error.message}`
            );
        }
    }

    getLogFilePath() {
        return this.logFilePath;
    }
}

export default FileLogger;