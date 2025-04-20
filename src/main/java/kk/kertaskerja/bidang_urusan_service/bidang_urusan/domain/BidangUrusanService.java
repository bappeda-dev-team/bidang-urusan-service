package kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BidangUrusanService {
    private final BidangUrusanRepository bidangUrusanRepository;
    public BidangUrusanService(BidangUrusanRepository bidangUrusanRepository) {
        this.bidangUrusanRepository = bidangUrusanRepository;
    }

    public Flux<BidangUrusan> getAllBidangUrusans() {
        return bidangUrusanRepository.findAll();
    }

    public Mono<BidangUrusan> addBidangUrusan(String kodeUrusan, String kodeBidangUrusan, String namaBidangUrusan) {
        return Mono.just(buildBidangUrusanTidakValid(kodeBidangUrusan, namaBidangUrusan))
                .flatMap(bidangUrusanRepository::save);
    }

    public static BidangUrusan buildBidangUrusanTidakValid(String kodeBidangUrusan, String namaBidangUrusan) {
        return BidangUrusan.of(kodeBidangUrusan, namaBidangUrusan, BidangUrusanStatus.TIDAK_VALID);
    }
}
