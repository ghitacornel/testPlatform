package platform.routes;

import contracts.companies.CompanyDetailsResponse;
import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.ProductClient;
import platform.fakers.ProductSellRequestFaker;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ProductRouteSale extends RouteBuilder {

    public static final int MINIMUM = 50;
    private static final int MAXIMUM = 2000;

    private final Random random = new Random();

    private final ProductClient productClient;
    private final CompanyClient companyClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=1000&delay=1000")
                .routeId("sale-product-route-timer")
                .multicast()
                .parallelProcessing()
                .to("direct:a")
                .to("direct:b")
                .to("direct:c")
                .to("direct:d")
                .to("direct:e")
        ;

        from("direct:a")
                .routeId("direct-a")
                .to("direct:sale-product-route");
        from("direct:b")
                .routeId("direct-b")
                .to("direct:sale-product-route");
        from("direct:c")
                .routeId("direct-c")
                .to("direct:sale-product-route");
        from("direct:d")
                .routeId("direct-d")
                .to("direct:sale-product-route");
        from("direct:e")
                .routeId("direct-e")
                .to("direct:sale-product-route");

        from("direct:sale-product-route")
                .routeId("sale-product-route")
                .setBody(exchange -> productClient.countAllActive())
                .filter(body().isLessThan(ProductRouteSale.MAXIMUM))
                .setBody(exchange -> ProductSellRequestFaker.fake())
                .process(exchange -> {
                    ProductSellRequest productSellRequest = exchange.getMessage().getBody(ProductSellRequest.class);
                    List<CompanyDetailsResponse> companyDetailsResponses = companyClient.findAll();
                    if (companyDetailsResponses.isEmpty()) {
                        exchange.setMessage(null);
                        return;
                    }
                    int index = random.nextInt(companyDetailsResponses.size());
                    productSellRequest.setCompanyId(companyDetailsResponses.get(index).getId());
                })
                .choice()
                .when(body().isNull()).log(LoggingLevel.WARN, "No companies available for creating products")
                .otherwise()
                .process(exchange -> productClient.sell(exchange.getMessage().getBody(ProductSellRequest.class)))
                .endChoice()
                .end();

    }

}

