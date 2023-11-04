package platform.routes;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.feign.client.ClientContract;
import platform.feign.client.ClientDetailsResponse;
import platform.feign.order.CreateOrderRequest;
import platform.feign.order.OrderContract;
import platform.feign.order.OrderDetailsResponse;
import platform.feign.product.ProductContract;
import platform.feign.product.ProductDetailsResponse;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderRoute extends RouteBuilder {

    private final Random random = new Random();

    private final OrderContract orderContract;
    private final ClientContract clientContract;
    private final ProductContract productContract;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=50&delay=1000")
                .routeId("create-order-route")
                .setBody(exchange -> CreateOrderRequest.builder().build())
                .process(exchange -> {

                    List<ClientDetailsResponse> clients = clientContract.findAll();
                    int index = random.nextInt(clients.size());

                    CreateOrderRequest createOrderRequest = exchange.getMessage().getBody(CreateOrderRequest.class);
                    createOrderRequest.setClientId(clients.get(index).getId());
                })
                .process(exchange -> {

                    List<ProductDetailsResponse> productDetailsResponses = productContract.findAllActive();
                    int index = random.nextInt(productDetailsResponses.size());
                    ProductDetailsResponse productDetailsResponse = productDetailsResponses.get(index);

                    CreateOrderRequest createOrderRequest = exchange.getMessage().getBody(CreateOrderRequest.class);
                    createOrderRequest.setProductId(productDetailsResponse.getId());
                    createOrderRequest.setQuantity(generateRandomQuantity(productDetailsResponse));
                })
                .setBody(exchange -> orderContract.create(exchange.getMessage().getBody(CreateOrderRequest.class)))
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
                .process(exchange -> orderContract.cancelById(exchange.getMessage().getBody(Integer.class)))
                .log("Cancel order ${body}")
                .end();

        from("timer://simpleTimer?period=50&delay=500")
                .routeId("complete-order-route")
                .setBody(exchange -> orderContract.findAllNew())
                .filter(body().method("size").isGreaterThan(0))
                .setBody(exchange -> {
                    List<OrderDetailsResponse> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index).getId();
                })
                .process(exchange -> orderContract.completeById(exchange.getMessage().getBody(Integer.class)))
                .log("Complete order ${body}")
                .end();

    }

    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

