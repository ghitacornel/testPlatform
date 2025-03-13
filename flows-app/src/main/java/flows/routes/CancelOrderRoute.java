package flows.routes;

import contracts.orders.OrderDetailsResponse;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrderRoute extends RouteBuilder {

    private final OrderClient orderClient;
    private final ProductClient productClient;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/orders/cancel")
                .patch("/{id}")
                .to("direct:cancel-order");

        from("direct:cancel-order")
                .routeId("cancel-order-route")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    OrderDetailsResponse orderDetailsResponse = orderClient.findById(id);
                    productClient.refill(orderDetailsResponse.getProductId(), orderDetailsResponse.getQuantity());
                })
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    orderClient.cancel(id);
                })
                .setBody().simple("${null}")
                .end();
    }

}

