package platform.routes;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ProductClient;
import platform.utils.GenerateUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ProductRouteCancel extends RouteBuilder {

    public static final int MINIMUM = 50;

    private final ProductClient productClient;

    private final Random random = new Random();
    private final AsyncCache<Integer, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.of(1, ChronoUnit.MINUTES))
            .buildAsync();

    @Override
    public void configure() {

        for (int i = 0; i < 5; i++) {
            from("timer://simpleTimer?period=5000&delay=1000")
                    .routeId("cancel-product-route-timer-" + i)
                    .multicast()
                    .parallelProcessing()
                    .to("direct:cancel-product-route-" + i)
            ;

            from("direct:cancel-product-route-" + i)
                    .routeId("cancel-product-route-" + i)
                    .setBody(exchange -> productClient.countAllActive())
                    .filter(body().isGreaterThan(ProductRouteCancel.MINIMUM))
                    .setBody(exchange -> productClient.findActiveIds())
                    .filter(body().method("size").isGreaterThan(0))
                    .setBody(exchange -> GenerateUtils.random(exchange, random, cache))
                    .choice()
                    .when(body().isNull()).log(LoggingLevel.WARN, "No products available for cancelling")
                    .otherwise()
                    .process(exchange -> {
                        try {
                            Integer id = exchange.getMessage().getBody(Integer.class);
                            productClient.cancel(id);
                            log.info("Product cancelled {}", id);
                        } catch (FeignException e) {
                            log.error(e.getMessage());
                        }
                    })
                    .endChoice()
                    .end();
        }
    }

}

