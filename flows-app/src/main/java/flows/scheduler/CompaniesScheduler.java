package flows.scheduler;

import flows.clients.CompanyClient;
import flows.clients.InvoiceClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class CompaniesScheduler {

    private final CompanyClient companyClient;
    private final InvoiceClient invoiceClient;
    private final ProductClient productClient;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void deleteRetired() {
        companyClient.findRetiredIds().forEach(id -> {
            if (productClient.existsByCompanyId(id)) {
                return;
            }
            if (invoiceClient.existsByCompanyId(id)) {
                return;
            }
            companyClient.delete(id);
            log.info("Retired company deleted {}", id);
        });
    }

}

