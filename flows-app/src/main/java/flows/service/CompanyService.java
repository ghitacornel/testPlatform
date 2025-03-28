package flows.service;

import commons.exceptions.ResourceNotFound;
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

        try {
            companyClient.retiring(id);
            log.info("retiring company {}", id);
        } catch (ResourceNotFound e) {
            log.warn("Company not found {}", id);
            return;
        }

        deleteRetiring(id);
    }

    public void deleteRetiring(Integer id) {

        productClient.findAllActiveIdsForCompany(id).forEach(orderClient::cancelByProductId);
        productClient.cancelByCompany(id);
        if (productClient.existsByCompanyId(id)) {
            return;
        }
        if (invoiceClient.existsByCompanyId(id)) {
            return;
        }

        try {
            companyClient.retired(id);
            log.info("retired company {}", id);
        } catch (ResourceNotFound e) {
            log.warn("Company not found {}", id);
        }

    }

}

