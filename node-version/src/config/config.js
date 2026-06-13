import dotenv from 'dotenv';

dotenv.config();

class ConfigLoader {

    constructor() {
        this.validate();
    }

    validate() {

        const required = [
            'NETWORK_HOST',
            'NETWORK_PORT',
            'NETWORK_TIMEOUT_MS',
            'PROBE_INTERVAL_MS',
            'PROBE_DURATION_MINUTES',
            'LATENCY_THRESHOLD_MS',
            'DB_CONNECT_THRESHOLD_MS',
            'DB_QUERY_THRESHOLD_MS',
            'DB_HOST',
            'DB_PORT',
            'DB_NAME',
            'DB_USER',
            'LOG_DIRECTORY',
            'GEMINI_API_KEY'
        ];

        for (const key of required) {
            if (!process.env[key]) {
                throw new Error(`Missing environment variable: ${key}`);
            }
        }

        const numericKeys = [
            'NETWORK_PORT',
            'NETWORK_TIMEOUT_MS',
            'PROBE_INTERVAL_MS',
            'PROBE_DURATION_MINUTES',
            'LATENCY_THRESHOLD_MS',
            'DB_CONNECT_THRESHOLD_MS',
            'DB_QUERY_THRESHOLD_MS',
            'DB_PORT'
        ];

        for (const key of numericKeys) {
            const value = Number(process.env[key]);

            if (Number.isNaN(value)) {
                throw new Error(
                    `Environment variable ${key} must be numeric`
                );
            }
        }
    }

    getNetworkHost() {
        return process.env.NETWORK_HOST;
    }

    getNetworkPort() {
        return Number(process.env.NETWORK_PORT);
    }

    getNetworkTimeoutMs() {
        return Number(process.env.NETWORK_TIMEOUT_MS);
    }

    getProbeIntervalMs() {
        return Number(process.env.PROBE_INTERVAL_MS);
    }

    getProbeDurationMinutes() {
        return Number(process.env.PROBE_DURATION_MINUTES);
    }

    getLatencyThresholdMs() {
        return Number(process.env.LATENCY_THRESHOLD_MS);
    }

    getDbConnectThresholdMs() {
        return Number(process.env.DB_CONNECT_THRESHOLD_MS);
    }

    getDbQueryThresholdMs() {
        return Number(process.env.DB_QUERY_THRESHOLD_MS);
    }

    getDbHost() {
        return process.env.DB_HOST;
    }

    getDbPort() {
        return Number(process.env.DB_PORT);
    }

    getDbName() {
        return process.env.DB_NAME;
    }

    getDbUser() {
        return process.env.DB_USER;
    }

    getDbPassword() {
        return process.env.DB_PASSWORD;
    }

    getLogDirectory() {
        return process.env.LOG_DIRECTORY;
    }
    getGeminiApiKey() {
        return process.env.GEMINI_API_KEY;
    }
}

export default ConfigLoader;