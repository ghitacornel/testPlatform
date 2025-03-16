package flows.routes;

import commons.exceptions.RestTechnicalException;
import feign.FeignException;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteOrderRoute extends RouteBuilder {

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
                    try {
                        orderClient.complete(id);
                    } catch (RestTechnicalException | FeignException e) {
                        log.error(e.getMessage());
                    }
                })
                .to("jms:queue:CompletedOrdersQueueName")
                .setBody().simple("${null}")
                .end();
    }

}

