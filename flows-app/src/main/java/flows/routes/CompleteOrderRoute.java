package flows.routes;

import contracts.invoices.InvoiceCreateRequest;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteOrderRoute extends RouteBuilder {

    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/orders/complete")
                .patch("/{id}")
                .to("direct:complete-order");

        from("direct:complete-order")
                .routeId("complete-order-route")
                .setBody(exchange -> exchange.getIn().getHeader("id", Integer.class))
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    invoiceClient.create(InvoiceCreateRequest.builder()
                            .orderId(id)
                            .build());
                })
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    orderClient.complete(id);
                })
                .to("jms:queue:CompletedOrdersQueueName")
                .setBody().simple("${null}")
                .end();
    }

}

