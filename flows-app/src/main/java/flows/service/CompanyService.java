package flows.service;

import commons.exceptions.ResourceNotFound;
import flows.clients.CompanyClient;
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
    private final CompanyServiceHelper helper;

    public void deleteCompany(Integer id) {

        try {
            companyClient.retire(id);
        } catch (ResourceNotFound e) {
            log.warn("Company not found {}", id);
            return;
        }

        productClient.findAllActiveIdsForCompany(id).forEach(orderClient::cancelByProductId);
        productClient.cancelByCompany(id);
        log.info("Company retired {}", id);
    }

    public void deleteRetired() {
        companyClient.findRetiredIds().forEach(helper::deleteRetired);
    }

}

