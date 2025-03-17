package flows.service;

import flows.clients.CompanyClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyClient companyClient;
    private final ProductClient productClient;
    private final OrderClient orderClient;

    public void deleteCompany(Integer id) {
        companyClient.unregister(id);
        productClient.findAllActiveForCompany(id)
                .forEach(product -> orderClient.cancelByProductId(product.getId()));
        productClient.cancelByCompany(id);
    }

}

