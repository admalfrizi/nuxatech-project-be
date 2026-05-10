package org.example.nuxatechprojectbe.health;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.nuxatechprojectbe.inventory.Services;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_check_logs", indexes = {
        @Index(name = "idx_log_service", columnList = "service_id"),
        @Index(name = "idx_log_time", columnList = "checked_at DESC")
})
@Getter
@Setter
@NoArgsConstructor
public class HealthCheckLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;

    @Column(nullable = false)
    private String status; // "UP" or "DOWN"

    @Column(nullable = false)
    private int latencyMs;

    @Column(name = "checked_at", updatable = false)
    private LocalDateTime checkedAt = LocalDateTime.now();
}
