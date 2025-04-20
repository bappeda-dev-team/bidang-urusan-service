package kk.kertaskerja.bidang_urusan_service.urusan;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class UrusanClient {
    private static final String URUSAN_ROOT_API = "/urusan/";
    private final WebClient webClient;

    public UrusanClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Urusan> getByKodeUrusan(String kodeUrusan) {
        return webClient.get()
                .uri(URUSAN_ROOT_API + kodeUrusan)
                .retrieve()
                .bodyToMono(Urusan.class)
                .timeout(Duration.ofSeconds(3), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class,
                        exception -> Mono.empty())
                .retryWhen(
                        Retry.backoff(3, Duration.ofMillis(100))
                ).onErrorResume(Exception.class,
                        exception -> Mono.empty());
    }
}
