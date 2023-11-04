package flows.routes;

import commons.model.IdResponse;
import flows.feign.order.CreateOrderRequest;
import flows.feign.order.OrderContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class CreateOrderRoute extends RouteBuilder {

    private final OrderContract orderContract;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/order/create")
                .post()
                .routeId("create-order-route")
                .consumes(APPLICATION_JSON_VALUE)
                .produces(APPLICATION_JSON_VALUE)
                .type(CreateOrderRequest.class)
                .outType(IdResponse.class)
                .to("direct:post-order");

        from("direct:post-order")
                .setBody(exchange -> orderContract.create(exchange.getMessage().getBody(CreateOrderRequest.class)))
                .log("Order ${body.id} created")
                .end();
    }

}

