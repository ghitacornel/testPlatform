package platform.routes;

import contracts.clients.ClientContract;
import contracts.clients.ClientDetailsResponse;
import contracts.flows.FlowsContract;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderContract;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductContract;
import contracts.products.ProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderRoute extends RouteBuilder {

    private final Random random = new Random();

    private final OrderContract orderContract;
    private final ClientContract clientContract;
    private final ProductContract productContract;
    private final FlowsContract flowsContract;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10&delay=1000")
                .routeId("create-order-route")
                .setBody(exchange -> {

                    Integer clientId;
                    {
                        List<ClientDetailsResponse> clients = clientContract.findAll();
                        int index = random.nextInt(clients.size());
                        clientId = clients.get(index).getId();
                    }

                    ProductDetailsResponse productDetailsResponse;
                    {
                        List<ProductDetailsResponse> productDetailsResponses = productContract.findAllActive();
                        int index = random.nextInt(productDetailsResponses.size());
                        productDetailsResponse = productDetailsResponses.get(index);
                    }

                    return CreateOrderRequest.builder()
                            .clientId(clientId)
                            .productId(productDetailsResponse.getId())
                            .quantity(generateRandomQuantity(productDetailsResponse))
                            .build();
                })
                .setBody(exchange -> flowsContract.createOrder(exchange.getMessage().getBody(CreateOrderRequest.class)).getId())
                .log("Create order ${body}")
                .end();

        from("timer://simpleTimer?period=1000&delay=1000")
                .routeId("cancel-order-route")
                .setBody(exchange -> orderContract.findAllNew())
                .filter(body().method("size").isGreaterThan(0))
                .setBody(exchange -> {
                    List<OrderDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> flowsContract.cancelOrder(exchange.getMessage().getBody(Integer.class)))
                .log("Cancel order ${body}")
                .end();

        from("timer://simpleTimer?period=10&delay=500")
                .routeId("complete-order-route")
                .setBody(exchange -> orderContract.findAllNew())
                .filter(body().method("size").isGreaterThan(0))
                .setBody(exchange -> {
                    List<OrderDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> flowsContract.completeOrder(exchange.getMessage().getBody(Integer.class)))
                .log("Complete order ${body}")
                .end();

    }

    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

