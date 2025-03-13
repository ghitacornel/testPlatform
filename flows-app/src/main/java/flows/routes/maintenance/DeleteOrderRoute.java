package flows.routes.maintenance;

import contracts.orders.OrderDetailsResponse;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteOrderRoute extends RouteBuilder {

    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10000&delay=1000")
                .routeId("delete-completed-order-route")
                .setBody(exchange -> orderClient.findAllCompleted())
                .split(body())
                .setBody(exchange -> {
                    OrderDetailsResponse orderDetailsResponse = exchange.getMessage().getBody(OrderDetailsResponse.class);
                    boolean exists = invoiceClient.existsByOrderId(orderDetailsResponse.getId());
                    if (exists) {
                        return null;
                    }
                    return orderDetailsResponse.getId();
                })
                .process(exchange -> {
                    Integer id = exchange.getMessage().getBody(Integer.class);
                    if (id != null) {
                        orderClient.delete(id);
                    }
                })
                .log("Completed order deleted ${body}")
                .end();
    }

}

