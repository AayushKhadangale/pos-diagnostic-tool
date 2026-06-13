import { Client } from 'pg';

import DbResult from '../model/DbResult.js';

class DatabaseProbe {

    constructor(
        host,
        port,
        database,
        user,
        password
    ) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    async executeProbe() {

        const timestamp = new Date();

        let dbConnectTimeMs = 0;
        let queryTimeMs = 0;

        let success = false;
        let errorMessage = null;

        let client;

        try {

            client = new Client({
                host: this.host,
                port: this.port,
                database: this.database,
                user: this.user,
                password: this.password
            });

            const connectionStart =
                process.hrtime.bigint();

            await client.connect();

            const connectionEnd =
                process.hrtime.bigint();

            dbConnectTimeMs =
                Number(connectionEnd - connectionStart)
                / 1_000_000;

            const queryStart =
                process.hrtime.bigint();

            await client.query('SELECT 1');

            const queryEnd =
                process.hrtime.bigint();

            queryTimeMs =
                Number(queryEnd - queryStart)
                / 1_000_000;

            success = true;

        } catch (error) {

            success = false;
            errorMessage = error.message;

        } finally {

            if (client) {
                try {
                    await client.end();
                } catch (_) {
                }
            }
        }

        return new DbResult(
            timestamp,
            dbConnectTimeMs,
            queryTimeMs,
            success,
            errorMessage
        );
    }
}

export default DatabaseProbe;