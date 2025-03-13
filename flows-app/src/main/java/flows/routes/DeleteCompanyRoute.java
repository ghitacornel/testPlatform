package flows.routes;

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
                .log("Unregister company ${header.id}")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    companyClient.unregister(id);
                })
                .log("Unregistered company ${header.id}")
                .log("Start cancelling orders for company ${header.id}")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    productClient.findAllActiveForCompany(id)
                            .forEach(product -> orderClient.findAllNewForProductId(product.getId())
                                    .forEach(orderDetailsResponse -> orderClient.cancel(orderDetailsResponse.getId())));
                })
                .log("End cancelling orders for company ${header.id}")
                .log("Cancel products for company ${header.id}")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    productClient.cancelByCompany(id);
                })
                .log("Cancelled products for company ${header.id}")
                .setBody().simple("${null}")
                .end();
    }

}

