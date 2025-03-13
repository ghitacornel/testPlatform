package platform.routes;

import contracts.clients.ClientDetailsResponse;
import contracts.orders.CreateOrderRequest;
import contracts.products.ProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.clients.ProductClient;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRouteCreate extends RouteBuilder {

    private final Random random = new Random();

    private final ClientClient clientClient;
    private final ProductClient productClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10&delay=1000")
                .routeId("create-order-route-timer")
                .multicast()
                .parallelProcessing()
                .to("direct:a")
                .to("direct:b")
                .to("direct:c")
                .to("direct:d")
                .to("direct:e")
        ;

        from("direct:a")
                .routeId("direct-a")
                .to("direct:create-order-route");
        from("direct:b")
                .routeId("direct-b")
                .to("direct:create-order-route");
        from("direct:c")
                .routeId("direct-c")
                .to("direct:create-order-route");
        from("direct:d")
                .routeId("direct-d")
                .to("direct:create-order-route");
        from("direct:e")
                .routeId("direct-e")
                .to("direct:create-order-route");

        from("direct:create-order-route")
                .routeId("create-order-route")
                .setBody(exchange -> {

                    Integer clientId;
                    {
                        List<ClientDetailsResponse> clients = clientClient.findAll();
                        if (clients.isEmpty()) {
                            log.error("no clients available");
                            return null;
                        }
                        int index = random.nextInt(clients.size());
                        clientId = clients.get(index).getId();
                    }

                    ProductDetailsResponse productDetailsResponse;
                    {
                        List<ProductDetailsResponse> productDetailsResponses = productClient.findAllActive();
                        if (productDetailsResponses.isEmpty()) {
                            log.error("no products available");
                            return null;
                        }
                        int index = random.nextInt(productDetailsResponses.size());
                        productDetailsResponse = productDetailsResponses.get(index);
                    }

                    return CreateOrderRequest.builder()
                            .clientId(clientId)
                            .productId(productDetailsResponse.getId())
                            .quantity(generateRandomQuantity(productDetailsResponse))
                            .build();
                })
                .choice()
                .when(body().isNull()).log("no order created")
                .otherwise()
                .setBody(exchange -> flowsClient.createOrder(exchange.getMessage().getBody(CreateOrderRequest.class)).getId())
                .log("Create order ${body}")
                .endChoice()
                .end();

    }

    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

