package platform.routes;

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
public class OrderRouteComplete extends RouteBuilder {

    private final Random random = new Random();

    private final OrderClient orderClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        from("timer://simpleTimer?period=10&delay=500")
                .routeId("complete-order-route-timer")
                .multicast()
                .parallelProcessing()
                .to("direct:complete-order-route")
                .to("direct:complete-order-route")
                .to("direct:complete-order-route")
                .to("direct:complete-order-route")
                .to("direct:complete-order-route")
        ;

        from("direct:complete-order-route")
                .routeId("complete-order-route")
                .setBody(exchange -> orderClient.findNewIds())
                .filter(body().method("size").isGreaterThan(0))
                .setBody(exchange -> {
                    List<?> data = exchange.getMessage().getBody(List.class);
                    int index = random.nextInt(data.size());
                    return data.get(index);
                })
                .process(exchange -> flowsClient.completeOrder(exchange.getMessage().getBody(Integer.class)))
                .log("Complete order ${body}")
                .end();

    }

}

