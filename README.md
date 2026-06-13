# POS Network & Database Diagnostic Tool

## Overview

A Java 17 command-line diagnostic tool that measures network and PostgreSQL database health between a POS client and server.

The tool helps identify whether slow transactions are caused by:

* Network latency
* Packet loss
* Database connection delays
* Database query delays

## Features

* TCP connection latency measurement
* Host reachability check
* Packet loss calculation
* PostgreSQL JDBC connectivity check
* Database connection timing
* Query round-trip timing using SELECT 1
* Continuous sampling
* Configurable thresholds
* Anomaly detection
* Summary reporting
* Timestamped log files

## Technology Stack

* Java 17
* Maven
* PostgreSQL JDBC Driver

## Project Structure

src/main/java

* config
* network
* database
* logging
* report
* service
* model

## Configuration

Edit:

src/main/resources/application.properties

to configure:

* Host
* Port
* Database credentials
* Thresholds
* Probe interval
* Probe duration

## Build

mvn clean compile

## Run

mvn exec:java

## Sample Output

Network => ProbeResult{latencyMs=0.74, reachable=true, success=true}

Database => DbResult{dbConnectTimeMs=9.36, queryTimeMs=1.06, success=true}

===== DIAGNOSTIC SUMMARY =====

Total Samples: 30
Packet Loss (%): 0.00
Verdict: System operating normally.
