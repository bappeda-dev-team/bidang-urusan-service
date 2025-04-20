package kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain;

public class BidangUrusanAlreadyExistsException extends RuntimeException {
    public BidangUrusanAlreadyExistsException(String kodeBidangUrusan) {
        super("Bidang urusan dengan kode " + kodeBidangUrusan + " sudah ada");
    }
}
