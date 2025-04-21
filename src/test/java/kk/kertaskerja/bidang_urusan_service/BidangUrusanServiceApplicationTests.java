package kk.kertaskerja.bidang_urusan_service;

import kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.Random.class)
@Testcontainers
class BidangUrusanServiceApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:16-alpine"));

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private BidangUrusanService bidangUrusanService;

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", BidangUrusanServiceApplicationTests::r2dbcUrl);
		registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
		registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);
		registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
	}

	private static String r2dbcUrl() {
		return String.format("r2dbc:postgresql://%s:%s/%s",
				postgreSQLContainer.getHost(),
				postgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
				postgreSQLContainer.getDatabaseName());
	}

	@Test
	void whenGetRequestToHomeThenReturnHealthStatus() {
		webTestClient.get()
				.uri("/")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.status").isEqualTo("BIDANG URUSAN SERVICE IS UP");
	}

	@Test
	void whenGetByKodeBidangUrusanFoundThenReturnBidangUrusan() {
		var kodeBidangUrusan = "X.XX";
		BidangUrusan bidangUrusan = BidangUrusan.of(kodeBidangUrusan, "Test Bidang Urusan", BidangUrusanStatus.VALID);

		given(bidangUrusanService.getByKodeBidangUrusan(kodeBidangUrusan))
				.willReturn(Mono.just(bidangUrusan));

		webTestClient.get()
				.uri("/bidang_urusan/" + kodeBidangUrusan)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(BidangUrusan.class).value(bidur -> {
					assertThat(bidur.kodeBidangUrusan()).isEqualTo(bidangUrusan.kodeBidangUrusan());
					assertThat(bidur.namaBidangUrusan()).isEqualTo(bidangUrusan.namaBidangUrusan());
				});
	}
}
