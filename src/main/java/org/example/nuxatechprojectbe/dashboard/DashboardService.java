package org.example.nuxatechprojectbe.dashboard;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.example.nuxatechprojectbe.dashboard.dto.LiveHealthStream;
import org.example.nuxatechprojectbe.dashboard.dto.MonitorHealthStats;
import org.example.nuxatechprojectbe.health.HealthCheckRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final HealthCheckRepository logRepository;
    private final MeterRegistry meterRegistry;

    public LiveHealthStream calculateLiveHealthStream() {
        // 1. Global Average Latency (Last 5 mins)
        LocalDateTime fiveMinsAgo = LocalDateTime.now().minusMinutes(5);
        Double avgLatency = logRepository.getGlobalAverageLatencySince(fiveMinsAgo);

        // 2. Historical Latencies for the Bar Chart (Last 20 pings)
        List<Integer> recentLatencies = logRepository.findRecentLatencies(PageRequest.of(0, 20));
        Collections.reverse(recentLatencies); // Oldest on left, newest on right

        return new LiveHealthStream(
                avgLatency != null ? avgLatency.intValue() : 0,
                true, // Websocket availability state
                recentLatencies
        );
    }

    public MonitorHealthStats calculateMonitorHealth() {
        // 1. Uptime Calculation via Micrometer
        double uptimeSeconds = meterRegistry.get("process.uptime").timeGauge().value();
        String formattedUptime = "0D 0H 0M";

        long days = (long) (uptimeSeconds / 86400);
        long hours = (long) ((uptimeSeconds % 86400) / 3600);
        long minutes = (long) ((uptimeSeconds % 3600) / 60);
        formattedUptime = String.format("%dD %02dH %02dM", days, hours, minutes);

        // 2. JVM Memory Calculation via Micrometer
        double maxMemory = meterRegistry.get("jvm.memory.max").gauge().value();
        double usedMemory = meterRegistry.get("jvm.memory.used").gauge().value();
        int heapUsage = maxMemory > 0
                ? (int) (usedMemory / maxMemory * 100)
                : 0;

        return new MonitorHealthStats(
                "UP",
                "60.0s INTERVAL", // Matches the fixedRate of your @Scheduled worker
                formattedUptime,
                heapUsage
        );
    }
}
