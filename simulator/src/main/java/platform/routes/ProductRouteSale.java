package platform.routes;

import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.ProductClient;
import platform.fakers.ProductSellRequestFaker;
import platform.utils.GenerateUtils;

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

        for (int i = 0; i < 5; i++) {
            from("timer://simpleTimer?period=500&delay=1000")
                .routeId("sale-product-route-timer-" + i)
                .multicast()
                    .parallelProcessing()
                    .to("direct:sale-product-route-" + i)
            ;

            from("direct:sale-product-route-" + i)
                .routeId("sale-product-route-" + i)
                .setBody(exchange -> productClient.countAllActive())
                .filter(body().isLessThan(ProductRouteSale.MAXIMUM))
                .setBody(exchange -> ProductSellRequestFaker.fake())
                .process(exchange -> {
                        ProductSellRequest productSellRequest = exchange.getMessage().getBody(ProductSellRequest.class);
                        Integer id = GenerateUtils.random(companyClient.findActiveIds(), random);
                        if (id == null) {
                            exchange.setMessage(null);
                            return;
                        }
                        productSellRequest.setCompanyId(id);
                    })
                .choice()
                    .when(body().isNull())
                        .log(LoggingLevel.WARN, "No companies available for creating products")
                    .otherwise()
                        .process(exchange -> {
                        try {
                            productClient.sell(exchange.getMessage().getBody(ProductSellRequest.class));
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    })
                    .endChoice()
                .end();
        }
    }

}

