import net from 'net';
import dns from 'dns/promises';

import ProbeResult from '../model/ProbeResult.js';

class NetworkProbe {

    constructor(host, port, timeoutMs) {
        this.host = host;
        this.port = port;
        this.timeoutMs = timeoutMs;
    }

    async executeProbe() {

        const timestamp = new Date();

        let reachable = false;
        let success = false;
        let latencyMs = 0;

        try {

            await dns.lookup(this.host);

            reachable = true;

            const startTime = process.hrtime.bigint();

            await new Promise((resolve, reject) => {

                const socket = new net.Socket();

                const timeout = setTimeout(() => {
                    socket.destroy();
                    reject(new Error('Connection timeout'));
                }, this.timeoutMs);

                socket.connect(this.port, this.host, () => {

                    const endTime = process.hrtime.bigint();

                    latencyMs =
                        Number(endTime - startTime) / 1_000_000;

                    clearTimeout(timeout);

                    success = true;

                    socket.destroy();

                    resolve();
                });

                socket.on('error', (err) => {

                    clearTimeout(timeout);

                    socket.destroy();

                    reject(err);
                });
            });

        } catch (error) {

            success = false;

        }

        return new ProbeResult(
            timestamp,
            latencyMs,
            reachable,
            success
        );
    }
}

export default NetworkProbe;