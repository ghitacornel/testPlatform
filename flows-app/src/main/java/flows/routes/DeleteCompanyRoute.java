package flows.routes;

import commons.exceptions.RestTechnicalException;
import feign.FeignException;
import flows.clients.CompanyClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCompanyRoute extends RouteBuilder {

    private final CompanyClient companyClient;
    private final ProductClient productClient;
    private final OrderClient orderClient;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/companies/delete")
                .delete("/{id}")
                .to("direct:delete-company");

        from("direct:delete-company")
                .routeId("delete-company-route")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    try {
                        companyClient.unregister(id);
                        productClient.findAllActiveForCompany(id)
                                .forEach(product -> orderClient.cancelByProductId(product.getId()));
                        productClient.cancelByCompany(id);
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .log("Cancelled products for company ${header.id}")
                .setBody().simple("${null}")
                .end();
    }

}

