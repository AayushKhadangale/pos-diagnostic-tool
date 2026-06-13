package org.example.network;

import org.example.model.ProbeResult;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;

public class NetworkProbe {

    private final String host;
    private final int port;
    private final int timeoutMs;

    public NetworkProbe(String host, int port, int timeoutMs) {
        this.host = host;
        this.port = port;
        this.timeoutMs = timeoutMs;
    }

    public ProbeResult executeProbe() {

        LocalDateTime timestamp = LocalDateTime.now();

        boolean reachable = false;
        boolean success = false;
        double latencyMs = 0.0;

        try {

            InetAddress inetAddress = InetAddress.getByName(host);
            reachable = inetAddress.isReachable(timeoutMs);

            long startTime = System.nanoTime();

            try (Socket socket = new Socket()) {

                socket.connect(
                        new InetSocketAddress(host, port),
                        timeoutMs
                );

                long endTime = System.nanoTime();

                latencyMs = (endTime - startTime) / 1_000_000.0;
                success = true;
            }

        } catch (Exception e) {

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