package platform.init;

import contracts.products.ProductSellRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.ProductClient;
import platform.fakers.ProductSellRequestFaker;

import static platform.routes.ProductRoute.MINIMUM;

@Component
@DependsOn("companiesSetup")
@RequiredArgsConstructor
class ProductsSetup {

    private final ProductClient client;
    private final CompanyClient companyClient;

    @PostConstruct
    void init() {
        long count = client.countAllActive();
        if (count >= MINIMUM) {
            return;
        }
        companyClient.findAll()
                .forEach(companyDetailsResponse -> {
                    ProductSellRequest productSellRequest = ProductSellRequestFaker.fake();
                    productSellRequest.setCompanyId(companyDetailsResponse.getId());
                    client.sell(productSellRequest);
                });
    }

}
