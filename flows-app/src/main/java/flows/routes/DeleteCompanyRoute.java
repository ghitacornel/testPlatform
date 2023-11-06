package flows.routes;

import contracts.companies.CompanyContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCompanyRoute extends RouteBuilder {

    private final CompanyContract companyContract;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/company/delete")
                .delete("/{id}")
                .to("direct:delete-company");

        from("direct:delete-company")
                .routeId("delete-company-route")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    companyContract.retire(id);
                })
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    companyContract.delete(id);
                })
                .end();
    }

}

