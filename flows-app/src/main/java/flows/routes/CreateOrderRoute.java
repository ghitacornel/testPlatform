package flows.routes;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.products.ProductBuyRequest;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class CreateOrderRoute extends RouteBuilder {

    private static final String PRODUCT_BUY_REQUEST = "productBuyRequest";

    private final OrderClient orderClient;
    private final ProductClient productClient;

    @Override
    public void configure() {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest()
                .path("/orders/create")
                .post()
                .consumes(APPLICATION_JSON_VALUE)
                .produces(APPLICATION_JSON_VALUE)
                .type(CreateOrderRequest.class)
                .outType(IdResponse.class)
                .to("direct:create-order");

        from("direct:create-order")
            .routeId("create-order-route")
            .setBody(exchange -> {
                    CreateOrderRequest createOrderRequest = exchange.getMessage().getBody(CreateOrderRequest.class);
                    ProductBuyRequest productBuyRequest = ProductBuyRequest.builder()
                            .clientId(createOrderRequest.getClientId())
                            .productId(createOrderRequest.getProductId())
                            .quantity(createOrderRequest.getQuantity())
                            .build();
                    try {
                        productClient.buy(productBuyRequest);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        return null;
                    }
                    exchange.getMessage().setHeader(PRODUCT_BUY_REQUEST, productBuyRequest);
                    return createOrderRequest;
                })
            .filter(body().isNotNull())
            .setBody(exchange -> {
                    try {
                        return orderClient.create(exchange.getMessage().getBody(CreateOrderRequest.class));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
            .filter(body().isNotNull())
            .log("Order ${body.id} created with ${header.productBuyRequest}")
            .removeHeader(PRODUCT_BUY_REQUEST)
                .end();
    }

}

