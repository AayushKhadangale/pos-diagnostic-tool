import { GoogleGenAI } from "@google/genai";

class ClaudeClient {

    constructor(apiKey) {
        this.client = new GoogleGenAI({
            apiKey
        });
    }

    async generate(prompt) {

        const response =
            await this.client.models.generateContent({
                model: "gemini-2.5-flash",
                contents: prompt
            });

        return response.text;
    }
}

export default ClaudeClient;