package org.example.nuxatechprojectbe.health;

import lombok.extern.slf4j.Slf4j;
import org.example.nuxatechprojectbe.inventory.InventoryRepository;
import org.example.nuxatechprojectbe.inventory.InventoryService;
import org.example.nuxatechprojectbe.inventory.Services;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class HealthCheckService {

    private final InventoryService inventoryService;
    private final HealthCheckRepository logRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public HealthCheckService(InventoryService inventoryService, HealthCheckRepository logRepository, SimpMessagingTemplate messagingTemplate) {
        this.inventoryService = inventoryService;
        this.logRepository = logRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    public void performCheck(Services serviceInfo) {
        long startTime = System.currentTimeMillis();
        String status = "DOWN";
        int latency = 0;
        String errorCode = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serviceInfo.getUrlOrIp()))
                    .GET()
                    .build();

            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            latency = (int) (System.currentTimeMillis() - startTime);

            if (response.statusCode() >= 200 && response.statusCode() < 400) {
                status = "UP";
            } else {
                errorCode = String.valueOf(response.statusCode());
                broadcastIncident(serviceInfo.getName(), "Received HTTP " + errorCode);
            }
        } catch (Exception e) {
            latency = (int) (System.currentTimeMillis() - startTime);
            log.error("Failed to ping service {}: {}", serviceInfo.getName(), e.getMessage());
            broadcastIncident(serviceInfo.getName(), "timed out. Attempting auto-recovery...");
        }

        // 1. Save to Time-Series Database
        HealthCheckLog logEntry = new HealthCheckLog();
        logEntry.setService(serviceInfo);
        logEntry.setStatus(status);
        logEntry.setLatencyMs(latency);
        logRepository.save(logEntry);

        inventoryService.updateServiceStatus(serviceInfo.getId(), status);

        messagingTemplate.convertAndSend("/topic/latency-stream", latency);
    }

    private void broadcastIncident(String serviceName, String reason) {
        String incidentLog = String.format("[%s] FAIL: %s %s",
                LocalDateTime.now().withNano(0), serviceName, reason);
        messagingTemplate.convertAndSend("/topic/incident-logs", incidentLog);
    }
}
