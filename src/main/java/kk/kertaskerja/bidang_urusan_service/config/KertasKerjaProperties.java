package kk.kertaskerja.bidang_urusan_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kertaskerja")
public class KertasKerjaProperties {
    private String status;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
