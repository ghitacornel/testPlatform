package platform.simulators;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.ProductClient;
import platform.utils.GenerateUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
class ProductRouteCancel {

    public static final int MINIMUM = 50;

    private final ProductClient productClient;

    private final Random random = new Random();
    private final AsyncCache<Integer, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.of(1, ChronoUnit.MINUTES))
            .buildAsync();

    @Scheduled(fixedRate = 5000, initialDelay = 1000)
    void simulate1() {
        simulate();
    }

    @Scheduled(fixedRate = 5000, initialDelay = 2000)
    void simulate2() {
        simulate();
    }

    @Scheduled(fixedRate = 5000, initialDelay = 3000)
    void simulate3() {
        simulate();
    }

    @Scheduled(fixedRate = 5000, initialDelay = 4000)
    void simulate4() {
        simulate();
    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    void simulate5() {
        simulate();
    }

    private void simulate() {

        long countAllActive = productClient.countAllActive();
        if (countAllActive < MINIMUM) {
            return;
        }

        List<Integer> activeIds = productClient.findActiveIds();
        Integer id = GenerateUtils.random(activeIds, random, cache);
        if (id == null) {
            log.warn("No products available for cancelling");
            return;
        }

        try {
            productClient.cancel(id);
            log.info("Product cancelled {}", id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}

