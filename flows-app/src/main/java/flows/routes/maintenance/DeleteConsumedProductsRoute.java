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
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return orderClient.existsByProductId(id) ? null : id;
                })
                .filter(body().isNotNull())
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return invoiceClient.existsByProductId(id) ? null : id;
                })
                .filter(body().isNotNull())
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    productClient.delete(id);
                })
                .log("Consumed product deleted ${body}")
                .end();
    }

}

