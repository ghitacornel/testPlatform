package platform.simulators;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import platform.clients.CompanyClient;
import platform.clients.ProductClient;
import platform.fakers.ProductSellRequestFaker;
import platform.utils.GenerateUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    public static final int MINIMUM = 50;
    private static final int MAXIMUM = 2000;

    private final ProductClient productClient;
    private final CompanyClient companyClient;

    private final Random random = new Random();
    private final AsyncCache<Integer, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.of(1, ChronoUnit.MINUTES))
            .buildAsync();

    @Async
    public void sell() {
        long countAllActive = productClient.countAllActive();
        if (countAllActive > MAXIMUM) {
            return;
        }

        ProductSellRequest fake = ProductSellRequestFaker.fake();

        Integer id = GenerateUtils.random(companyClient.findActiveIds(), random);
        if (id == null) {
            log.warn("No companies available for creating products");
            return;
        }
        fake.setCompanyId(id);

        productClient.sell(fake);
    }

    @Async
    public void cancel() {
        long countAllActive = productClient.countAllActive();
        if (countAllActive < MINIMUM) {
            return;
        }

        List<Integer> activeIds = productClient.findActiveIds();
        Integer id = GenerateUtils.random(activeIds, random, cache);
        if (id == null) {
            log.warn("No product to cancel");
            return;
        }

        productClient.cancel(id);
        log.info("Product cancelled {}", id);
    }

}

