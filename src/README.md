# POS Network & Database Diagnostic Tool (Java)

## Overview

This project is a Java-based POS Network & Database Diagnostic Tool developed as part of the Abmiro Java Developer Assignment.

The application continuously monitors network and database health by collecting latency metrics, validating connectivity, detecting anomalies, generating reports, and maintaining detailed diagnostic logs.

The tool is designed to help identify connectivity and performance issues affecting Point-of-Sale (POS) environments.

---

## Features

### Network Diagnostics

* Host reachability validation
* TCP connection testing
* Network latency measurement
* Packet loss calculation
* Periodic network sampling

### Database Diagnostics

* PostgreSQL connectivity validation
* Database connection timing measurement
* Query execution timing measurement
* Database health verification using test queries

### Anomaly Detection

* High network latency detection
* Slow database connection detection
* Slow query execution detection
* Threshold-based alert generation

### Reporting

* Minimum latency calculation
* Maximum latency calculation
* Average latency calculation
* Jitter calculation
* Packet loss calculation
* Database performance summary
* System health verdict

### Logging

* Timestamped log file generation
* Network diagnostic logging
* Database diagnostic logging
* Anomaly logging
* Final summary logging

---

## Technology Stack

| Component     | Technology             |
| ------------- | ---------------------- |
| Language      | Java 17                |
| Build Tool    | Maven                  |
| Database      | PostgreSQL             |
| JDBC Driver   | PostgreSQL JDBC        |
| Configuration | application.properties |

---

## Project Structure

```text
src/main/java/org/example

├── config
│   └── ConfigLoader.java
│
├── network
│   └── NetworkProbe.java
│
├── database
│   └── DatabaseProbe.java
│
├── logging
│   └── FileLogger.java
│
├── report
│   └── ReportGenerator.java
│
├── service
│   └── DiagnosticService.java
│
├── model
│   ├── ProbeResult.java
│   ├── DbResult.java
│   └── AnomalyEvent.java
│
└── Main.java
```

---

## Prerequisites

* Java 17
* Maven 3.8+
* PostgreSQL

Verify installation:

```bash
java -version
mvn -version
psql --version
```

---

## Configuration

Configuration is stored in:

```text
src/main/resources/application.properties
```

Example:

```properties
network.host=google.com
network.port=443
network.timeout.ms=3000

probe.interval.ms=2000
probe.duration.minutes=1

latency.threshold.ms=100

db.url=jdbc:postgresql://localhost:5432/postgres
db.username=postgres
db.password=password

db.connect.threshold.ms=100
db.query.threshold.ms=100

log.directory=logs
```

---

## Building the Project

Compile the application:

```bash
mvn clean compile
```

Package the application:

```bash
mvn clean package
```

---

## Running the Application

Using Maven:

```bash
mvn exec:java
```

Or using the generated JAR:

```bash
java -jar target/pos-diagnostic-tool.jar
```

---

## Sample Output

```text
====================================
POS Network & Database Diagnostic Tool
====================================

Network => ProbeResult{...}

Database => DbResult{...}

------------------------------------------------------------

===== DIAGNOSTIC SUMMARY =====

Total Samples: 30
Min Latency (ms): 0.21
Max Latency (ms): 0.91
Average Latency (ms): 0.75
Jitter (ms): 0.11
Packet Loss (%): 0.00

Average DB Connect Time (ms): 11.88
Average DB Query Time (ms): 1.15

Anomaly Count: 0

Verdict: System operating normally.
```

---

## Log Files

Diagnostic logs are automatically generated in the configured log directory.

Example:

```text
logs/
└── diagnostic_20260612_193157.log
```

Log contents include:

* Network probe results
* Database probe results
* Detected anomalies
* Final diagnostic summary

---

## Architecture

The application follows a modular design.

### ConfigLoader

Responsible for loading application configuration.

### NetworkProbe

Performs network reachability and latency measurements.

### DatabaseProbe

Validates PostgreSQL connectivity and measures database performance.

### FileLogger

Persists diagnostic information to timestamped log files.

### ReportGenerator

Calculates diagnostic statistics and generates final reports.

### DiagnosticService

Coordinates the complete diagnostic workflow.

---

## Design Decisions

* Externalized configuration using application.properties
* Separation of concerns across dedicated components
* Fresh network probe executed during every sample cycle
* Fresh database connection established during every database probe
* Threshold-driven anomaly detection
* Timestamped logging for auditability

---

## Future Enhancements

* Historical trend analysis
* Email alerts
* Dashboard visualization
* Multi-host monitoring
* AI-assisted root cause analysis

---

## Author

Aayush Khadangale

Abmiro Java Developer Assignment – Phase 1 (Java Implementation)
