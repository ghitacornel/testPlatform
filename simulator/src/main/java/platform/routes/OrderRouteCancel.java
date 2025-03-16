package platform.routes;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.FlowsClient;
import platform.clients.OrderClient;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderRouteCancel extends RouteBuilder {

    private final Random random = new Random();

    private final OrderClient orderClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        for (int i = 0; i < 5; i++) {
            from("timer://simpleTimer?period=500&delay=1000")
                    .routeId("cancel-order-route-timer-" + i)
                    .multicast()
                    .parallelProcessing()
                    .to("direct:cancel-order-route-" + i)
            ;

            from("direct:cancel-order-route-" + i)
                    .routeId("cancel-order-route-" + i)
                    .setBody(exchange -> orderClient.findNewIds())
                    .filter(body().method("size").isGreaterThan(0))
                    .setBody(exchange -> {
                        List<?> data = exchange.getMessage().getBody(List.class);
                        int index = random.nextInt(data.size());
                        return data.get(index);
                    })
                    .process(exchange -> flowsClient.cancelOrder(exchange.getMessage().getBody(Integer.class)))
                    .log("Cancel order ${body}")
                    .end();
        }

    }

}

