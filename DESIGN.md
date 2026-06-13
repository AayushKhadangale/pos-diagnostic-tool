# Design Decisions

## Project Overview

The POS Network & Database Diagnostic Tool was designed to help operators identify connectivity and database performance issues between a client POS terminal and a central POS server.

The primary goal was to provide measurable diagnostics rather than relying on assumptions when troubleshooting transaction delays, failed saves, or intermittent connectivity issues.

---

# Architecture

The application follows a modular architecture with clear separation of responsibilities.

```text
Main
 │
 ▼
DiagnosticService
 │
 ├── NetworkProbe
 ├── DatabaseProbe
 ├── FileLogger
 └── ReportGenerator
```

---

# Design Decisions

## 1. Separation of Concerns

Each component has a single responsibility.

### ConfigLoader

Loads configuration values from external configuration sources.

### NetworkProbe

Measures:

* Host reachability
* TCP connection latency
* Network success/failure status

### DatabaseProbe

Measures:

* PostgreSQL connection establishment time
* Query execution time
* Database availability

### FileLogger

Handles all file-based logging operations.

### ReportGenerator

Calculates statistics and generates summary reports.

### DiagnosticService

Coordinates the complete diagnostic workflow.

---

## 2. Configuration Externalization

All configurable values are stored outside source code.

Examples:

* Network host
* Network port
* Database credentials
* Threshold values
* Probe interval
* Probe duration

Reason:

Avoid hardcoded environment-specific values.

---

## 3. Independent Measurements

Network and database diagnostics are measured independently.

Reason:

A network issue and a database issue may occur separately and should be reported independently.

---

## 4. New Database Connection Per Probe

A new PostgreSQL connection is created for every database probe.

Reason:

The objective is to measure connection establishment latency.

Connection pooling would hide this metric and distort results.

---

## 5. Threshold-Based Anomaly Detection

Anomalies are generated when metrics exceed configurable thresholds.

Examples:

* High network latency
* Slow database connection
* Slow database query

Reason:

Operators should be alerted only when values exceed expected operating ranges.

---

## 6. Logging Strategy

All probe results and anomalies are written to timestamped log files.

Reason:

Provides historical evidence for troubleshooting and analysis.

---

## 7. Node.js Migration Decisions

During migration:

* Java blocking operations were converted to async/await
* JDBC was replaced with pg
* application.properties was replaced with .env
* Thread.sleep() was replaced with Promise-based delays

The original workflow and reporting logic were preserved.

---

# AI Features

## AI Root Cause Summary

Provides a plain-language explanation of probable causes based on collected metrics and anomalies.

## Natural Language Report

Converts technical statistics into a report suitable for a non-technical operator.

---

# Known Limitations

* Single host monitoring
* PostgreSQL only
* No graphical dashboard
* No historical trend analysis
* No alert notifications

---

# Future Enhancements

* Dashboard UI
* Email alerts
* Multi-host monitoring
* Historical reporting
* Predictive anomaly detection
