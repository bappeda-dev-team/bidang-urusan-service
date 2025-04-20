package kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

public record BidangUrusan(
        @Id Long id,

        String kodeBidangUrusan,
        String namaBidangUrusan,
        BidangUrusanStatus status,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        @Version
        int version
) {
    public static BidangUrusan of(String kodeBidangUrusan, String namaBidangUrusan, BidangUrusanStatus status) {
        return new BidangUrusan(null, kodeBidangUrusan, namaBidangUrusan, status, null, null, 0);
    }
}
