package kk.kertaskerja.bidang_urusan_service.bidang_urusan.web;

import kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain.BidangUrusan;
import kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain.BidangUrusanService;
import kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain.BidangUrusanStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebFluxTest(BidangUrusanController.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class BidangUrusanControllerWebFluxTests {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private BidangUrusanService bidangUrusanService;

    @Test
    void whenUrusanNotFoundThenBidangUrusanTidakValid() {
        var bidUrRequest = new BidangUrusanRequest("Z", "Z.ZZ", "TEST BIDANG URUSAN");
        var expectedBidangUrusan = BidangUrusanService.buildBidangUrusanTidakValid(
                bidUrRequest.kodeBidangUrusan(),
                bidUrRequest.namaBidangUrusan()
        );

        given(bidangUrusanService.addBidangUrusan(
                bidUrRequest.kodeUrusan(),
                bidUrRequest.kodeBidangUrusan(),
                bidUrRequest.namaBidangUrusan()
        )).willReturn(Mono.just(expectedBidangUrusan));

        webTestClient.post()
                .uri("/bidang_urusan")
                .bodyValue(bidUrRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(BidangUrusan.class).value(actualBidangUrusan -> {
                    assertThat(actualBidangUrusan).isNotNull();
                    assertThat(actualBidangUrusan.status()).isEqualTo(BidangUrusanStatus.TIDAK_VALID);
                });
    }

    @Test
    void whenKodeUrusanBlankThenFormInvalid() {
        var bidUrRequest = new BidangUrusanRequest("", "Z.ZZ", "TEST BIDANG URUSAN");
        webTestClient.post()
                .uri("/bidang_urusan")
                .bodyValue(bidUrRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo("400")
                .jsonPath("$.message").isEqualTo("Form tidak sesuai")
                .jsonPath("$.errors.kodeUrusan").exists()
                .jsonPath("$.errors.kodeUrusan[0]").isEqualTo("Kode urusan wajib terisi");
    }

    @Test
    void whenKodeBidangUrusanBlankThenFormInvalid() {
        var bidUrRequest = new BidangUrusanRequest("Z", "", "TEST BIDANG URUSAN");
        webTestClient.post()
                .uri("/bidang_urusan")
                .bodyValue(bidUrRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo("400")
                .jsonPath("$.message").isEqualTo("Form tidak sesuai")
                .jsonPath("$.errors.kodeBidangUrusan").exists()
                .jsonPath("$.errors.kodeBidangUrusan.length()").isEqualTo(2)
                .jsonPath("$.errors.kodeBidangUrusan").value(errors -> {
                    var errMessage = asStringList(errors);
                    assertThat(errMessage).contains("Format kode tidak valid", "Kode bidang urusan wajib terisi");
                });
    }

    private List<String> asStringList(Object val) {
        return ((List<?>) val).stream()
                .map(Object::toString)
                .toList();
    }
}
