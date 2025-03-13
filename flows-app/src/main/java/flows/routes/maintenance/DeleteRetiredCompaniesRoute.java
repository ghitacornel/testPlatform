package flows.routes.maintenance;

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
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return productClient.existsByCompanyId(id) ? null : id;
                })
                .filter(body().isNotNull())
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return invoiceClient.existsByCompanyId(id) ? null : id;
                })
                .filter(body().isNotNull())
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    companyClient.delete(id);
                })
                .log("Retired company deleted ${body}")
                .end();
    }

}

