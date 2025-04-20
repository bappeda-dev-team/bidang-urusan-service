package kk.kertaskerja.bidang_urusan_service.urusan;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .bodyToMono(Urusan.class);
    }
}
