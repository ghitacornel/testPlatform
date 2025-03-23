package flows.service;

import commons.exceptions.ResourceNotFound;
import contracts.products.ProductDetailsResponse;
import flows.clients.CompanyClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

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

        List<ProductDetailsResponse> products = productClient.findAllActiveForCompany(id);
        productClient.cancelByCompany(id);
        products.forEach(product -> orderClient.cancelByProductId(product.getId()));

        log.info("Company retired {}", id);
    }

    @Async
    public void deleteRetired() {
        companyClient.findRetiredIds().forEach(helper::deleteRetired);
    }

}

