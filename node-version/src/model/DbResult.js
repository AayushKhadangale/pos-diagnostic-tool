class DbResult {

    constructor(
        timestamp,
        dbConnectTimeMs,
        queryTimeMs,
        success,
        errorMessage = null
    ) {
        this.timestamp = timestamp;
        this.dbConnectTimeMs = dbConnectTimeMs;
        this.queryTimeMs = queryTimeMs;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    toString() {

        const result = {
            timestamp: this.timestamp,
            dbConnectTimeMs: this.dbConnectTimeMs,
            queryTimeMs: this.queryTimeMs,
            success: this.success
        };

        if (!this.success && this.errorMessage) {
            result.errorMessage = this.errorMessage;
        }

        return JSON.stringify(result);
    }
}

export default DbResult;