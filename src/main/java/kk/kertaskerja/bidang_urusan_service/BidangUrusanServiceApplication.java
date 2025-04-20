package kk.kertaskerja.bidang_urusan_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BidangUrusanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidangUrusanServiceApplication.class, args);
	}

}
