package kk.kertaskerja.bidang_urusan_service.bidang_urusan.domain;

import kk.kertaskerja.bidang_urusan_service.config.DataConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataR2dbcTest
@Import(DataConfig.class)
@Testcontainers
@TestMethodOrder(MethodOrderer.Random.class)
public class BidangUrusanRepositoryR2dbcTests {
    @Container
    static PostgreSQLContainer<?> postgresql =
            new PostgreSQLContainer<>(
                    DockerImageName.parse("postgres:16-alpine"));

    @Autowired
    private BidangUrusanRepository bidangUrusanRepository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", BidangUrusanRepositoryR2dbcTests::r2dbcUrl);
        registry.add("spring.r2dbc.username", postgresql::getUsername);
        registry.add("spring.r2dbc.password", postgresql::getPassword);
        registry.add("spring.flyway.url", postgresql::getJdbcUrl);
    }

    private static String r2dbcUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                postgresql.getHost(),
                postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgresql.getDatabaseName());
    }

    @Test
    void createTidakValidBidangUrusan() {
        var bidangUrusanTidakValid = BidangUrusanService
                .buildBidangUrusanTidakValid("Z.ZZ", "TEST BIDANG URUSAN");
        StepVerifier.create(bidangUrusanRepository.save(bidangUrusanTidakValid))
                .consumeNextWith(bidUrusanResp -> {
                    assertEquals("Z.ZZ", bidUrusanResp.kodeBidangUrusan());
                    assertEquals("TEST BIDANG URUSAN", bidUrusanResp.namaBidangUrusan());
                    assertEquals(BidangUrusanStatus.TIDAK_VALID, bidUrusanResp.status());
                }).verifyComplete();
    }

    @Test
    void createValidBidangUrusan() {
        var bidangUrusanValid = BidangUrusanService
                .buildBidangUrusanValid("X.XX", "TEST BIDANG URUSAN");
        StepVerifier.create(bidangUrusanRepository.save(bidangUrusanValid))
                .consumeNextWith(bidUrusanResp -> {
                    assertEquals("X.XX", bidUrusanResp.kodeBidangUrusan());
                    assertEquals("TEST BIDANG URUSAN", bidUrusanResp.namaBidangUrusan());
                    assertEquals(BidangUrusanStatus.VALID, bidUrusanResp.status());
                }).verifyComplete();
    }

}
