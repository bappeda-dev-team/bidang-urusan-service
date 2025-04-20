package kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BidangUrusanRepository extends ReactiveCrudRepository<BidangUrusan, Long> {
    Mono<Boolean> existsByKodeBidangUrusan(String kodeBidangUrusan);
}
