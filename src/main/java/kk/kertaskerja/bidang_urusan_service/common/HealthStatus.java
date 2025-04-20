package kk.kertaskerja.bidang_urusan_service.common;

import java.time.Instant;

public record HealthStatus(
        String status,
        Instant timestamp
) {}
