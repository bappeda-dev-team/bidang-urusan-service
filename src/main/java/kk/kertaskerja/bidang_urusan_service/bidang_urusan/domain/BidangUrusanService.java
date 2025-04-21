package kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain;

import kk.kertaskerja.bidang_urusan_service.urusan.UrusanClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BidangUrusanService {
    private final UrusanClient urusanClient;
    private final BidangUrusanRepository bidangUrusanRepository;
    public BidangUrusanService(UrusanClient urusanClient, BidangUrusanRepository bidangUrusanRepository) {
        this.urusanClient = urusanClient;
        this.bidangUrusanRepository = bidangUrusanRepository;
    }

    public Flux<BidangUrusan> getAllBidangUrusans() {
        return bidangUrusanRepository.findAll();
    }
    public Mono<BidangUrusan> getByKodeBidangUrusan(String kodeBidangUrusan) {
        return bidangUrusanRepository.existsByKodeBidangUrusan(kodeBidangUrusan)
                .flatMap(exists -> {
                    if (!exists) return Mono.error(new BidangUrusanNotFoundException(kodeBidangUrusan));
                    return bidangUrusanRepository.findByKodeBidangUrusan(kodeBidangUrusan);
                });
    }

    public Mono<BidangUrusan> addBidangUrusan(String kodeUrusan, String kodeBidangUrusan, String namaBidangUrusan) {
        return bidangUrusanRepository.existsByKodeBidangUrusan(kodeBidangUrusan)
                .flatMap(exists -> {
                    if (exists) return Mono.error(new BidangUrusanAlreadyExistsException(kodeBidangUrusan));
                    return urusanClient.getByKodeUrusan(kodeUrusan)
                            .map(urusan -> buildBidangUrusanValid(kodeBidangUrusan, namaBidangUrusan))
                            .defaultIfEmpty(buildBidangUrusanTidakValid(kodeBidangUrusan, namaBidangUrusan));
                })
                .flatMap(bidangUrusanRepository::save);
    }

    public static BidangUrusan buildBidangUrusanValid(String kodeBidangUrusan, String namaBidangUrusan) {
        return BidangUrusan.of(kodeBidangUrusan, namaBidangUrusan, BidangUrusanStatus.VALID);
    }

    public static BidangUrusan buildBidangUrusanTidakValid(String kodeBidangUrusan, String namaBidangUrusan) {
        return BidangUrusan.of(kodeBidangUrusan, namaBidangUrusan, BidangUrusanStatus.TIDAK_VALID);
    }
}
