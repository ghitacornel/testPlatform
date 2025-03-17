package platform.simulators;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.FlowsClient;
import platform.clients.OrderClient;
import platform.utils.GenerateUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
class OrderRouteComplete {

    private final OrderClient orderClient;
    private final FlowsClient flowsClient;

    private final Random random = new Random();
    private final AsyncCache<Integer, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.of(1, ChronoUnit.MINUTES))
            .buildAsync();

    @Scheduled(fixedRate = 200, initialDelay = 1000)
    void simulate1() {
        simulate();
    }

    @Scheduled(fixedRate = 200, initialDelay = 1200)
    void simulate2() {
        simulate();
    }

    @Scheduled(fixedRate = 200, initialDelay = 1400)
    void simulate3() {
        simulate();
    }

    @Scheduled(fixedRate = 200, initialDelay = 1600)
    void simulate4() {
        simulate();
    }

    @Scheduled(fixedRate = 200, initialDelay = 1800)
    void simulate5() {
        simulate();
    }

    private void simulate() {
        List<Integer> newIds = orderClient.findNewIds();
        Integer id = GenerateUtils.random(newIds, random, cache);
        if (id == null) {
            log.warn("No order to complete");
            return;
        }
        try {
            flowsClient.completeOrder(id);
            log.info("Order completed {}", id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
