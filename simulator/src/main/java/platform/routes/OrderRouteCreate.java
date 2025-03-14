package platform.routes;

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

        from("timer://simpleTimer?period=100&delay=1000")
                .routeId("create-order-route-timer")
                .multicast()
                .parallelProcessing()
                .to("direct:create-order-route")
                .to("direct:create-order-route")
                .to("direct:create-order-route")
                .to("direct:create-order-route")
                .to("direct:create-order-route")
        ;

        from("direct:create-order-route")
                .routeId("create-order-route")
                .setBody(exchange -> {

                    Integer clientId;
                    {
                        List<Integer> clients = clientClient.findActiveIds();
                        if (clients.isEmpty()) {
                            log.error("no clients available");
                            return null;
                        }
                        int index = random.nextInt(clients.size());
                        clientId = clients.get(index);
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

