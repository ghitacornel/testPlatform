package platform.routes.orders;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.routes.orders.feign.CreateOrderRequest;
import platform.routes.orders.feign.OrderContract;
import platform.routes.orders.feign.OrderDetailsResponse;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderRoute extends RouteBuilder {

    private final Random random = new Random();

    private final OrderContract orderContract;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=50&delay=1000")
                .routeId("create-order-route")
                .setBody(exchange -> CreateOrderRequest.builder()
                        .clientId(-1)
                        .productId(-1)
                        .quantity(random.nextInt(10000) + 100)
                        .build())
                .process(exchange -> orderContract.create(exchange.getMessage().getBody(CreateOrderRequest.class)))
                .log("Created order : ${body.id}")
                .end();

        from("timer://simpleTimer?period=1000&delay=1000")
                .routeId("cancel-order-route")
                .setBody(exchange -> {
                    List<OrderDetailsResponse> data = orderContract.findAllNew();
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .process(exchange -> orderContract.deleteById(exchange.getMessage().getBody(OrderDetailsResponse.class).getId()))
                .log("Cancel order : ${body}")
                .end();

        from("timer://simpleTimer?period=50&delay=500")
                .routeId("complete-order-route")
                .setBody(exchange -> {
                    List<OrderDetailsResponse> data = orderContract.findAllNew();
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .process(exchange -> orderContract.completeById(exchange.getMessage().getBody(OrderDetailsResponse.class).getId()))
                .log("Complete order : ${body}")
                .end();

    }

//    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
//        int quantity = random.nextInt(productDetailsResponse.getQuantity());
//        if (quantity == 0) quantity++;
//        return quantity;
//    }

}

