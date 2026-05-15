package org.example.nuxatechprojectbe.dashboard.dto;

public record MonitorHealthStats(
    String actuatorStatus,
    String heartbeatInterval,
    String uptime,
    int heapUsagePercent
) {}
