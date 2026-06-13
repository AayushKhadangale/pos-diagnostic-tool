package org.example.database;

import org.example.model.DbResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class DatabaseProbe {

    private final String dbUrl;
    private final String username;
    private final String password;

    public DatabaseProbe(String dbUrl,
                         String username,
                         String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    public DbResult executeProbe() {

        LocalDateTime timestamp = LocalDateTime.now();

        double dbConnectTimeMs = 0.0;
        double queryTimeMs = 0.0;

        boolean success = false;
        String errorMessage = null;

        try {

            long connectionStartTime = System.nanoTime();

            Connection connection = DriverManager.getConnection(
                    dbUrl,
                    username,
                    password
            );

            long connectionEndTime = System.nanoTime();

            dbConnectTimeMs =
                    (connectionEndTime - connectionStartTime)
                            / 1_000_000.0;

            long queryStartTime = System.nanoTime();

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("SELECT 1");

                 ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    resultSet.getInt(1);
                }
            }

            long queryEndTime = System.nanoTime();

            queryTimeMs =
                    (queryEndTime - queryStartTime)
                            / 1_000_000.0;

            success = true;

            connection.close();

        } catch (Exception e) {

            success = false;
            errorMessage = e.getMessage();
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