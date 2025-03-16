package platform.routes;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.FlowsClient;
import platform.clients.OrderClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderRouteComplete extends RouteBuilder {

    private final OrderClient orderClient;
    private final FlowsClient flowsClient;

    private final Random random = new Random();
    private final Cache<Integer, Integer> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.of(10, ChronoUnit.SECONDS))
            .build();

    @Override
    public void configure() {

        for (int i = 0; i < 5; i++) {
            from("timer://simpleTimer?period=10&delay=500")
                    .routeId("complete-order-route-timer-" + i)
                    .multicast()
                    .parallelProcessing()
                    .to("direct:complete-order-route-" + i)
            ;

            from("direct:complete-order-route-" + i)
                    .routeId("complete-order-route-" + i)
                    .setBody(exchange -> orderClient.findNewIds())
                    .filter(body().method("size").isGreaterThan(0))
                    .setBody(exchange -> {
                        List<?> data = exchange.getMessage().getBody(List.class);

                        int index;
                        do {
                            index = random.nextInt(data.size());
                        } while (cache.getIfPresent(index) != null);

                        return data.get(index);
                    })
                    .process(exchange -> flowsClient.completeOrder(exchange.getMessage().getBody(Integer.class)))
                    .process(exchange -> cache.put(exchange.getMessage().getBody(Integer.class), exchange.getMessage().getBody(Integer.class)))
                    .log("Complete order ${body}")
                    .end();
        }
    }

}

