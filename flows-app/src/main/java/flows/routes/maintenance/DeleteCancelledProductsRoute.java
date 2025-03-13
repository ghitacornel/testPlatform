package flows.routes.maintenance;

import flows.clients.InvoiceClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCancelledProductsRoute extends RouteBuilder {

    private final ProductClient productClient;
    private final InvoiceClient invoiceClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("delete-cancelled-products-route")
                .setBody(exchange -> productClient.findCancelledIds())
                .split(body())
                .parallelProcessing()
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return invoiceClient.existsByProductId(id) ? null : id;
                })
                .filter(body().isNotNull())
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    productClient.delete(id);
                })
                .log("Cancelled product deleted ${body}")
                .end();
    }

}

