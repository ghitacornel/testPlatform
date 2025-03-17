package flows.service;

import flows.clients.CompanyClient;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyClient companyClient;
    private final ProductClient productClient;
    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    public void deleteCompany(Integer id) {
        companyClient.retire(id);
        productClient.findAllActiveForCompany(id)
                .forEach(product -> orderClient.cancelByProductId(product.getId()));
        productClient.cancelByCompany(id);
        log.info("Company retired {}", id);
    }

    public void deleteRetired() {
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

