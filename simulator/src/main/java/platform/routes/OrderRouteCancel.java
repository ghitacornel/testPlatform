package platform.routes;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.FlowsClient;
import platform.clients.OrderClient;
import platform.utils.GenerateUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderRouteCancel extends RouteBuilder {

    private final OrderClient orderClient;
    private final FlowsClient flowsClient;

    private final Random random = new Random();
    private final AsyncCache<Integer, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.of(1, ChronoUnit.MINUTES))
            .buildAsync();

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
                    .setBody(exchange -> GenerateUtils.random(exchange, random, cache))
                    .process(exchange -> {
                        try {
                            Integer id = exchange.getMessage().getBody(Integer.class);
                            flowsClient.cancelOrder(id);
                            log.info("Order cancelled {}", id);
                        } catch (FeignException e) {
                            log.error(e.getMessage());
                        }
                    })
                    .end();
        }

    }

}

