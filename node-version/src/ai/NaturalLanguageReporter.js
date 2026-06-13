class NaturalLanguageReporter {

    constructor(claudeClient) {
        this.claudeClient = claudeClient;
    }

    async generate(summary) {

        const prompt = `
Explain this diagnostic result to a non-technical POS operator.

Requirements:
- Simple English
- No technical jargon
- Maximum 150 words
- Mention if action is needed

Diagnostic Summary:

${summary}
`;

        return await this.claudeClient.generate(prompt);
    }
}

export default NaturalLanguageReporter;