package platform.simulators;

import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.CompanyClient;
import platform.clients.ProductClient;
import platform.fakers.ProductSellRequestFaker;
import platform.utils.GenerateUtils;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductRouteSale {

    public static final int MINIMUM = 50;
    private static final int MAXIMUM = 2000;

    private final Random random = new Random();

    private final ProductClient productClient;
    private final CompanyClient companyClient;

    @Scheduled(fixedRate = 500, initialDelay = 1000)
    void simulate1() {
        simulate();
    }

    @Scheduled(fixedRate = 500, initialDelay = 1500)
    void simulate2() {
        simulate();
    }

    @Scheduled(fixedRate = 500, initialDelay = 2000)
    void simulate3() {
        simulate();
    }

    @Scheduled(fixedRate = 500, initialDelay = 2500)
    void simulate4() {
        simulate();
    }

    @Scheduled(fixedRate = 500, initialDelay = 3000)
    void simulate5() {
        simulate();
    }

    private void simulate() {

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

        try {
            productClient.sell(fake);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}

