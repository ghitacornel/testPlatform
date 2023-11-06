package flows.routes;

import contracts.orders.OrderContract;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrderRoute extends RouteBuilder {

    private final OrderContract orderContract;
    private final ProductContract productContract;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/order/cancel")
                .patch("/{id}")
                .to("direct:cancel-order");

        from("direct:cancel-order")
                .routeId("cancel-order-route")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    OrderDetailsResponse orderDetailsResponse = orderContract.findById(id);
                    productContract.refill(orderDetailsResponse.getProductId(), orderDetailsResponse.getQuantity());
                })
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    orderContract.cancel(id);
                })
                .log("Order ${header.id} cancelled")
                .end();
    }

}

