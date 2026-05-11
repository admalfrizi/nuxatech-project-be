package org.example.nuxatechprojectbe.health;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HealthCheckRepository extends JpaRepository<HealthCheckLog, Long> {
    @Query("SELECT h.latencyMs FROM HealthCheckLog h ORDER BY h.checkedAt DESC")
    List<Integer> findRecentLatencies(Pageable pageable);

    // 2. Calculate the global average latency over the last X minutes
    @Query("SELECT COALESCE(AVG(h.latencyMs), 0) FROM HealthCheckLog h WHERE h.checkedAt >= :timeLimit")
    Double getGlobalAverageLatencySince(@Param("timeLimit") LocalDateTime timeLimit);
}
