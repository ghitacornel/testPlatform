package flows.service;

import flows.clients.CompanyClient;
import flows.clients.InvoiceClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class CompanyServiceHelper {

    private final CompanyClient companyClient;
    private final ProductClient productClient;
    private final InvoiceClient invoiceClient;

    @Async
    void deleteRetired(Integer id) {
        if (productClient.existsByCompanyId(id)) {
            return;
        }
        if (invoiceClient.existsByCompanyId(id)) {
            return;
        }
        companyClient.delete(id);
        log.info("Retired company deleted {}", id);
    }

}

