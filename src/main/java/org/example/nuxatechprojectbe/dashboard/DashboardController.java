package org.example.nuxatechprojectbe.dashboard;

import lombok.RequiredArgsConstructor;
import org.example.nuxatechprojectbe.common.response.ResponseHandler;
import org.example.nuxatechprojectbe.common.response.ResponseHelper;
import org.example.nuxatechprojectbe.dashboard.dto.LiveHealthStream;
import org.example.nuxatechprojectbe.dashboard.dto.MonitorHealthStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/live-health")
    public ResponseEntity<ResponseHelper<LiveHealthStream>> getLiveHealthStream() {
        return ResponseHandler.generateResponse(
                dashboardService.calculateLiveHealthStream(),
                "Succesfully Trace Health Data",
                HttpStatus.OK,
                true
        );
    }

    @GetMapping("/monitor-health")
    public ResponseEntity<ResponseHelper<MonitorHealthStats>> getMonitorHealth() {
        return ResponseHandler.generateResponse(
                dashboardService.calculateMonitorHealth(),
                "Succesfully Trace Monitor Health",
                HttpStatus.OK,
                true
        );
    }
}
