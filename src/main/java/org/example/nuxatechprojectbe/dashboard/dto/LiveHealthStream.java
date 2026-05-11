package org.example.nuxatechprojectbe.dashboard.dto;

import java.util.List;

public record LiveHealthStream(
    int globalAverageLatencyMs,
    boolean isWebsocketActive,
    List<Integer> historicalLatencies
) {}
