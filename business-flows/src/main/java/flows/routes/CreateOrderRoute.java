package flows.routes;

import commons.model.IdResponse;
import flows.feign.order.CreateOrderRequest;
import flows.feign.order.OrderContract;
import flows.feign.product.ProductBuyRequest;
import flows.feign.product.ProductContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderRoute extends RouteBuilder {

    private final OrderContract orderContract;
    private final ProductContract productContract;

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
                .process(exchange -> {
                    CreateOrderRequest createOrderRequest = exchange.getMessage().getBody(CreateOrderRequest.class);
                    ProductBuyRequest productBuyRequest = ProductBuyRequest.builder()
                            .clientId(createOrderRequest.getClientId())
                            .productId(createOrderRequest.getProductId())
                            .quantity(createOrderRequest.getQuantity())
                            .build();
                    productContract.buy(productBuyRequest);
                    log.info(productBuyRequest.toString());
                })
                .setBody(exchange -> orderContract.create(exchange.getMessage().getBody(CreateOrderRequest.class)))
                .log("Order ${body.id} created")
                .end();
    }

}

