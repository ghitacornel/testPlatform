package platform.routes;

import contracts.clients.ClientContract;
import contracts.clients.ClientDetailsResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.FlowsClient;
import platform.clients.OrderClient;
import platform.clients.ProductClient;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRoute extends RouteBuilder {

    private final Random random = new Random();

    private final OrderClient orderClient;
    private final ClientContract clientContract;
    private final ProductClient productClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10&delay=1000")
                .routeId("create-order-route")
                .setBody(exchange -> {

                    Integer clientId;
                    {
                        List<ClientDetailsResponse> clients = clientContract.findAll();
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

        from("timer://simpleTimer?period=1000&delay=1000")
                .routeId("cancel-order-route")
                .setBody(exchange -> orderClient.findAllNew())
                .filter(body().method("size").isGreaterThan(0))
                .setBody(exchange -> {
                    List<OrderDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> flowsClient.cancelOrder(exchange.getMessage().getBody(Integer.class)))
                .log("Cancel order ${body}")
                .end();

        from("timer://simpleTimer?period=10&delay=500")
                .routeId("complete-order-route")
                .setBody(exchange -> orderClient.findAllNew())
                .filter(body().method("size").isGreaterThan(0))
                .setBody(exchange -> {
                    List<OrderDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> flowsClient.completeOrder(exchange.getMessage().getBody(Integer.class)))
                .log("Complete order ${body}")
                .end();

    }

    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

