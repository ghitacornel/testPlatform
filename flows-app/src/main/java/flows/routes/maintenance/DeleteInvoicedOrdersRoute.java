package flows.routes.maintenance;

import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteInvoicedOrdersRoute extends RouteBuilder {

    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("delete-invoiced-order-route")
                .setBody(exchange -> orderClient.findInvoicedIds())
                .split(body())
                .parallelProcessing()
                .setBody(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    return invoiceClient.existsByOrderId(id) ? null : id;
                })
                .filter(body().isNotNull())// delete only those that were invoiced and invoiced was first deleted
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    orderClient.delete(id);
                })
                .log("Invoiced order deleted ${body}")
                .end();
    }

}

