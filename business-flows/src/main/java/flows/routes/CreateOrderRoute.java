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

        restConfiguration()
                .component("servlet")
                .contextPath("/")
                .bindingMode(RestBindingMode.auto);

        rest("/api")
                .get("/all")
                .produces(APPLICATION_JSON_VALUE)
                .to("direct:all");

        from("direct:all")
                .setBody(exchange -> CreateOrderRequest.builder()
                        .clientId(1)
                        .productId(2)
                        .quantity(3)
                        .build())
                .log("{body}")
                .end();

//        rest()
//                .path("/order")
//                .post()
//                .consumes(APPLICATION_JSON_VALUE)
//                .produces(APPLICATION_JSON_VALUE)
//                .type(CreateOrderRequest.class)
//                .outType(IdResponse.class)
//                .to("direct:order-create");
//
//        from("direct:order-create")
//                .setBody(exchange -> orderContract.create(exchange.getMessage().getBody(CreateOrderRequest.class)))
//                .log("{body}")
//                .end();
    }

}
