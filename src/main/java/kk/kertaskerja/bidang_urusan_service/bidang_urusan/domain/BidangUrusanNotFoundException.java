package kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain;

public class BidangUrusanNotFoundException extends RuntimeException {
    public BidangUrusanNotFoundException(String kodeBidangUrusan) {
        super("Bidang urusan dengan kode " + kodeBidangUrusan + " tidak ditemukan");
    }
}
