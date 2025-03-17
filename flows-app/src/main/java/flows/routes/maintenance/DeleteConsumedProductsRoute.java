package flows.routes.maintenance;

import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteConsumedProductsRoute extends RouteBuilder {

    private final ProductClient productClient;
    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
            .routeId("delete-consumed-products-route")
            .setBody(exchange -> productClient.findConsumedIds())
            .split(body())
                .parallelProcessing()
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    try {
                        if (orderClient.existsByProductId(id)) {
                            return;
                        }
                        if (invoiceClient.existsByProductId(id)) {
                            return;
                        }
                        productClient.delete(id);
                        log.info("Consumed product deleted {}", id);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                })
            .end();
    }

}

