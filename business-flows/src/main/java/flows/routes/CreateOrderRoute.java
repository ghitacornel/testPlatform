package flows.routes;

import commons.model.IdResponse;
import flows.feign.order.CreateOrderRequest;
import flows.feign.order.OrderContract;
import flows.feign.product.ProductBuyRequest;
import flows.feign.product.ProductContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class CreateOrderRoute extends RouteBuilder {

    private static final String PRODUCT_BUY_REQUEST = "productBuyRequest";

    private final OrderContract orderContract;
    private final ProductContract productContract;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/order/create")
                .post()
                .consumes(APPLICATION_JSON_VALUE)
                .produces(APPLICATION_JSON_VALUE)
                .type(CreateOrderRequest.class)
                .outType(IdResponse.class)
                .to("direct:post-order");

        from("direct:post-order")
                .routeId("create-order-route")
                .process(exchange -> {
                    CreateOrderRequest createOrderRequest = exchange.getMessage().getBody(CreateOrderRequest.class);
                    ProductBuyRequest productBuyRequest = ProductBuyRequest.builder()
                            .clientId(createOrderRequest.getClientId())
                            .productId(createOrderRequest.getProductId())
                            .quantity(createOrderRequest.getQuantity())
                            .build();
                    productContract.buy(productBuyRequest);
                    exchange.getMessage().setHeader(PRODUCT_BUY_REQUEST, productBuyRequest);
                })
                .setBody(exchange -> orderContract.create(exchange.getMessage().getBody(CreateOrderRequest.class)))
                .log("Order ${body.id} created with ${header.productBuyRequest}")
                .removeHeader(PRODUCT_BUY_REQUEST)
                .end();
    }

}

