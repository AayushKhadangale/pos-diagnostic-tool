class AnomalyEvent {

    constructor(
        timestamp,
        type,
        message,
        actualValue,
        thresholdValue
    ) {
        this.timestamp = timestamp;
        this.type = type;
        this.message = message;
        this.actualValue = actualValue;
        this.thresholdValue = thresholdValue;
    }

    toString() {
        return JSON.stringify({
            timestamp: this.timestamp,
            type: this.type,
            message: this.message,
            actualValue: this.actualValue,
            thresholdValue: this.thresholdValue
        });
    }
}

export default AnomalyEvent;