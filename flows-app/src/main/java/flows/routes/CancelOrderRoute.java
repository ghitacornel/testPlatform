package flows.routes;

import commons.exceptions.RestTechnicalException;
import contracts.orders.OrderDetailsResponse;
import feign.FeignException;
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
                    try {
                        OrderDetailsResponse orderDetailsResponse = orderClient.findById(id);
                        productClient.refill(orderDetailsResponse.getProductId(), orderDetailsResponse.getQuantity());
                        orderClient.cancel(id);
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .setBody()
                .simple("${null}")
                .end();
    }

}

