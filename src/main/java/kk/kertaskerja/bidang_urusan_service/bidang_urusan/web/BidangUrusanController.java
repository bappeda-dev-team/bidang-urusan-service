package kk.kertaskerja.bidang_urusan_service.bidang_urusan.web;

import jakarta.validation.Valid;
import kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain.BidangUrusan;
import kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain.BidangUrusanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bidang_urusan")
public class BidangUrusanController {
    private final BidangUrusanService bidangUrusanService;
    public BidangUrusanController(BidangUrusanService bidangUrusanService) {
        this.bidangUrusanService = bidangUrusanService;
    }

    @GetMapping
    public Flux<BidangUrusan> getAllBidangUrusans() {
        return bidangUrusanService.getAllBidangUrusans();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BidangUrusan> addBidangUrusan(@RequestBody @Valid BidangUrusanRequest request) {
        return bidangUrusanService
                .addBidangUrusan(request.kodeUrusan(),
                                request.kodeBidangUrusan(),
                                request.namaBidangUrusan());
    }
}
