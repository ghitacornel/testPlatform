package flows.routes;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteOrderRoute extends RouteBuilder {

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/order/complete")
                .patch("/{id}")
                .to("direct:complete-order");

        from("direct:complete-order")
                .routeId("complete-order-route")
                .setBody(exchange -> exchange.getIn().getHeader("id", Integer.class))
                .to("jms:queue:CompletedOrdersQueueName")
                .end();
    }

}

