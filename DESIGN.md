# Design Document

## Project Overview

The POS Network & Database Diagnostic Tool is a Java 17 command-line application designed to diagnose connectivity and performance issues between a POS client machine and a central server hosting a PostgreSQL database.

The tool measures network performance, database responsiveness, packet loss, and latency statistics, then generates a summary report to help operators determine whether transaction delays are caused by network conditions or database performance.

---

## Design Goals

* Separation of concerns
* Config-driven architecture
* No hardcoded values
* Easy maintenance and extension
* Production-style error handling
* Independent network and database diagnostics

---

## Architecture

The application follows a layered design where each class has a single responsibility.

### Config Layer

**ConfigLoader**

Responsible for loading configuration values from the application.properties file.

Configuration includes:

* Network host
* Network port
* Timeouts
* Probe intervals
* Thresholds
* Database connection details

---

### Network Layer

**NetworkProbe**

Responsible for:

* Reachability testing
* TCP connection timing
* Network latency measurement

The implementation uses:

* Socket
* InetSocketAddress
* InetAddress
* System.nanoTime()

The result is returned through a ProbeResult object.

---

### Database Layer

**DatabaseProbe**

Responsible for:

* PostgreSQL connectivity testing
* JDBC connection timing
* Query execution timing

A lightweight query:

SELECT 1

is executed to measure database round-trip performance.

Results are returned through a DbResult object.

---

### Model Layer

The model layer contains simple POJO classes used to transfer data between components.

#### ProbeResult

Stores:

* Timestamp
* Latency
* Reachability
* Success status

#### DbResult

Stores:

* Connection time
* Query execution time
* Success status
* Error information

#### AnomalyEvent

Stores:

* Timestamp
* Type
* Message
* Measured value
* Threshold value

---

### Service Layer

**DiagnosticService**

Acts as the orchestrator of the application.

Responsibilities:

* Load configuration
* Execute probes
* Store results
* Detect anomalies
* Trigger logging
* Generate reports

The service continuously executes probes at configurable intervals until the configured duration is reached.

---

### Logging Layer

**FileLogger**

Responsible for writing:

* Probe results
* Database results
* Anomaly events
* Summary reports

to timestamped log files.

---

### Reporting Layer

**ReportGenerator**

Calculates:

* Minimum latency
* Maximum latency
* Average latency
* Jitter
* Packet loss percentage
* Average DB connection time
* Average DB query time
* Anomaly count

and generates a final verdict.

---

## Execution Flow

1. Main starts the application.
2. DiagnosticService is created.
3. ConfigLoader loads configuration.
4. NetworkProbe and DatabaseProbe are initialized.
5. Probes run at the configured interval.
6. Results are collected and logged.
7. Threshold violations generate anomaly events.
8. After the configured duration, a final report is generated.
9. The report is displayed on the console and written to a log file.

---

## Design Decisions

### External Configuration

All configuration values are stored outside the source code to avoid recompilation when environments change.

### Constructor Injection

Dependencies are passed through constructors rather than instantiated internally, reducing coupling between classes.

### Result Objects

ProbeResult and DbResult separate network metrics from database metrics, making reporting more accurate and maintainable.

### Error Handling

All network and database failures are handled gracefully without terminating the application.

---

## Current Limitations

* Single-threaded implementation
* PostgreSQL-specific database support
* Local file-based logging
* Command-line interface only

---

## Future Enhancements

* Multi-threaded probe execution
* Dashboard UI
* Historical trend analysis
* CSV/JSON export
* AI-powered root-cause analysis
* Support for additional databases

---

## Conclusion

The application follows clean object-oriented design principles, separates responsibilities across layers, externalizes configuration, and provides reliable network and database diagnostics for POS environments.
