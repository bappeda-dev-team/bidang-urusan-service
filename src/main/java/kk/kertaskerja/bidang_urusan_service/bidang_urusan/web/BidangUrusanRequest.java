package kk.kertaskerja.bidang_urusan_service.bidang_urusan.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BidangUrusanRequest(

        @NotBlank(message = "Kode urusan wajib terisi")
        String kodeUrusan,

        @NotBlank(message = "Kode bidang urusan wajib terisi")
        @Pattern(
                regexp = "^([A-Z]|\\d)\\.([A-Z]{2}|\\d{2})$",
                message = "Format kode tidak valid"
        )
        String kodeBidangUrusan,

        @NotBlank(message = "Nama bidang urusan wajib terisi")
        String namaBidangUrusan
) {}
