# POS Network & Database Diagnostic Tool (Node.js)

## Overview

This project is the Node.js implementation of the POS Network & Database Diagnostic Tool developed as part of the Abmiro Java Developer Assignment.

The application continuously monitors network and database connectivity, collects performance metrics, detects anomalies, generates diagnostic reports, and logs all observations for later analysis.

The Node.js version was created by migrating the original Java implementation while preserving functional behavior and maintaining a clean, modular architecture.

---

## Features

### Network Diagnostics

* Host reachability validation
* TCP connection testing
* Network latency measurement
* Packet loss calculation
* Continuous sampling at configurable intervals

### Database Diagnostics

* PostgreSQL connectivity validation
* Database connection timing measurement
* Query execution timing measurement
* Failure detection and reporting

### Anomaly Detection

* High network latency detection
* Slow database connection detection
* Slow database query detection
* Event logging for detected anomalies

### Reporting

* Diagnostic summary generation
* Average latency calculation
* Jitter calculation
* Packet loss analysis
* Database performance analysis
* Final health verdict

### Logging

* Timestamped log file generation
* Network probe logging
* Database probe logging
* Anomaly event logging
* Final summary logging

---

## Technology Stack

| Component       | Technology              |
| --------------- | ----------------------- |
| Runtime         | Node.js                 |
| Database        | PostgreSQL              |
| Configuration   | dotenv                  |
| Database Driver | pg                      |
| Language        | JavaScript (ES Modules) |

---

## Project Structure

```text
src
├── config
│   └── config.js
│
├── model
│   ├── ProbeResult.js
│   ├── DbResult.js
│   └── AnomalyEvent.js
│
├── probes
│   ├── NetworkProbe.js
│   └── DatabaseProbe.js
│
├── logging
│   └── FileLogger.js
│
├── report
│   └── ReportGenerator.js
│
├── service
│   └── DiagnosticService.js
│
└── index.js
```

---

## Prerequisites

* Node.js 18+
* PostgreSQL
* npm

Verify installation:

```bash
node --version
npm --version
psql --version
```

---

## Installation

Clone the repository:

```bash
git clone <repository-url>
cd pos-diagnostic-tool
```

Install dependencies:

```bash
npm install
```

---

## Environment Configuration

Create a `.env` file in the project root.

Example:

```env
NETWORK_HOST=google.com
NETWORK_PORT=443
NETWORK_TIMEOUT_MS=3000

PROBE_INTERVAL_MS=2000
PROBE_DURATION_MINUTES=1

LATENCY_THRESHOLD_MS=100

DB_HOST=localhost
DB_PORT=5432
DB_NAME=postgres
DB_USER=postgres
DB_PASSWORD=password

DB_CONNECT_THRESHOLD_MS=100
DB_QUERY_THRESHOLD_MS=100

LOG_DIRECTORY=logs
```

Update the database configuration according to your local PostgreSQL setup.

---

## Running the Application

Start the diagnostic tool:

```bash
node src/index.js
```

or

```bash
npm start
```

---

## Sample Output

```text
====================================
POS Network & Database Diagnostic Tool
====================================

Network => ProbeResult(...)
Database => DbResult(...)

------------------------------------------------------------

===== DIAGNOSTIC SUMMARY =====

Total Samples: 30
Average Latency (ms): 1.47
Packet Loss (%): 0.00
Average DB Connect Time (ms): 6.17
Average DB Query Time (ms): 1.11
Anomaly Count: 0
Verdict: System operating normally.
```

---

## Log Files

Log files are automatically generated inside the configured log directory.

Example:

```text
logs/
└── diagnostic_20260613_154348.log
```

The log file contains:

* Network probe results
* Database probe results
* Anomaly events
* Final diagnostic summary

---

## Design Notes

The migration preserves the behavior of the Java implementation:

* Fresh network probe executed for every sample
* Fresh PostgreSQL connection created for every database probe
* Sequential execution model maintained
* Configurable thresholds preserved
* Reporting calculations preserved
* Logging behavior preserved

Node.js asynchronous operations are implemented using async/await while maintaining functional parity with the original Java version.

---

## Future Improvements

* AI-powered root cause analysis
* Natural language diagnostic summaries
* Historical trend analysis
* Alert notifications
* Dashboard visualization

---

## Author

Aayush Khadangale

Abmiro Java Developer Assignment – Phase 2 (Node.js Migration)
