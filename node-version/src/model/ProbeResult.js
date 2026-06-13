class ProbeResult {

    constructor(
        timestamp,
        latencyMs,
        reachable,
        success
    ) {
        this.timestamp = timestamp;
        this.latencyMs = latencyMs;
        this.reachable = reachable;
        this.success = success;
    }

    toString() {
        return JSON.stringify({
            timestamp: this.timestamp,
            latencyMs: this.latencyMs,
            reachable: this.reachable,
            success: this.success
        });
    }
}

export default ProbeResult;