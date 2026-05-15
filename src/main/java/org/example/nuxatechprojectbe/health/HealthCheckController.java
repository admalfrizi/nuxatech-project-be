package org.example.nuxatechprojectbe.health;

import lombok.RequiredArgsConstructor;
import org.example.nuxatechprojectbe.common.response.ResponseHandler;
import org.example.nuxatechprojectbe.common.response.ResponseHelper;
import org.example.nuxatechprojectbe.inventory.InventoryService;
import org.example.nuxatechprojectbe.inventory.Services;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthCheckController {

    private final InventoryService inventoryService;
    private final HealthCheckService healthCheckService;

    @PostMapping("/force-check/{id}")
    public ResponseEntity<ResponseHelper<Map<String, Object>>> forceCheck(@PathVariable UUID id) {
        Services targetService = inventoryService.getServiceById(id);

        healthCheckService.performCheck(targetService);
        return ResponseHandler.generateResponse(
                Map.of(
                        "serviceId", targetService.getId(),
                        "newStatus", targetService.getStatus()
                ),
                "Manual health check completed successfully",
                HttpStatus.OK,
                true
        );
    }
}
