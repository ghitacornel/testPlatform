package flows.routes.maintenance;

import contracts.invoices.InvoiceCreateRequest;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartInvoiceRoute extends RouteBuilder {

    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("start-invoice-route")
                .setBody(exchange -> orderClient.findCompletedIds())
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    invoiceClient.create(InvoiceCreateRequest.builder()
                            .orderId(id)
                            .build());
                })
                .to("jms:queue:CompletedOrdersQueueName")
                .end();
    }

}

