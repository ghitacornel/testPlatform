package platform.routes;

import contracts.orders.OrderDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.FlowsClient;
import platform.clients.OrderClient;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRouteCancel extends RouteBuilder {

    private final Random random = new Random();

    private final OrderClient orderClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=500&delay=1000")
                .routeId("cancel-order-route-timer")
                .multicast()
                .parallelProcessing()
                .to("direct:a")
//                .to("direct:b")
//                .to("direct:c")
//                .to("direct:d")
//                .to("direct:e")
        ;

        from("direct:a")
                .routeId("direct-a")
                .to("direct:cancel-order-route");
//        from("direct:b")
//                .routeId("direct-b")
//                .to("direct:cancel-order-route");
//        from("direct:c")
//                .routeId("direct-c")
//                .to("direct:cancel-order-route");
//        from("direct:d")
//                .routeId("direct-d")
//                .to("direct:cancel-order-route");
//        from("direct:e")
//                .routeId("direct-e")
//                .to("direct:cancel-order-route");


        from("direct:cancel-order-route")
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

    }

}

