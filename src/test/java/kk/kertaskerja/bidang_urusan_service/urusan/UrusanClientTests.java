package kk.kertaskerja.bidang_urusan_service.urusan;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.Random.class)
public class UrusanClientTests {
    private MockWebServer mockWebServer;
    private UrusanClient urusanClient;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        var webClient = WebClient.builder()
                .baseUrl(this.mockWebServer.url("/").uri().toString())
                .build();
        this.urusanClient = new UrusanClient(webClient);
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void whenUrusanExistsThenReturnUrusan() {
        var kodeUrusan = "X";

        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("""
                        {
                            "kodeUrusan": "%s",
                            "kodeBidangUrusan": "X.XX",
                            "namaBidangUrusan": "Test Bidang Urusan"
                        }
                        """.formatted(kodeUrusan));
        mockWebServer.enqueue(mockResponse);

        Mono<Urusan> urusan = urusanClient.getByKodeUrusan(kodeUrusan);

        StepVerifier.create(urusan)
                .expectNextMatches(
                        ur -> ur.kodeUrusan().equals(kodeUrusan)
                ).verifyComplete();
    }

    @Test
    void whenUrusanNotExistsThenReturnEmpty() {
        var kodeUrusan = "Z";
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(404);
        mockWebServer.enqueue(mockResponse);

        StepVerifier.create(urusanClient.getByKodeUrusan(kodeUrusan))
                .expectNextCount(0)
                .verifyComplete();
    }
}
