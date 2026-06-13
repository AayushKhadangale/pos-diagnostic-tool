class RootCauseAnalyzer {

    constructor(claudeClient) {
        this.claudeClient = claudeClient;
    }

    async analyze(data) {

        const prompt = `
You are a network diagnostics expert.

Analyze the provided metrics.

Possible causes:
- Network latency
- Packet loss
- Database performance
- DNS issue
- Intermittent failure
- No issue detected

Respond in less than 100 words.

Metrics:

${JSON.stringify(data, null, 2)}
`;

        return await this.claudeClient.generate(prompt);
    }
}

export default RootCauseAnalyzer;