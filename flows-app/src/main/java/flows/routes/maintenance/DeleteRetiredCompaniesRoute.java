package flows.routes.maintenance;

import commons.exceptions.RestTechnicalException;
import feign.FeignException;
import flows.clients.CompanyClient;
import flows.clients.InvoiceClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteRetiredCompaniesRoute extends RouteBuilder {

    private final CompanyClient companyClient;
    private final InvoiceClient invoiceClient;
    private final ProductClient productClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("delete-retired-companies-route")
                .setBody(exchange -> companyClient.findRetiredIds())
                .split(body())
                .parallelProcessing()
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    try {
                        if (productClient.existsByCompanyId(id)) {
                            return;
                        }
                        if (invoiceClient.existsByCompanyId(id)) {
                            return;
                        }
                        companyClient.delete(id);
                        log.info("Retired company deleted {}", id);
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .end();
    }

}

