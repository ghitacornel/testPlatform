package flows.routes;

import contracts.orders.OrderContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteOrderRoute extends RouteBuilder {

    private final OrderContract orderContract;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/order/complete")
                .patch("/{id}")
                .to("direct:complete-order");

        from("direct:complete-order")
                .routeId("complete-order-route")
                .process(exchange -> {
                    Integer id = exchange.getIn().getHeader("id", Integer.class);
                    orderContract.complete(id);
                })
                .setBody(exchange -> exchange.getIn().getHeader("id", Integer.class))
                .to("jms:queue:CompletedOrdersQueueName")
                .end();
    }

}

