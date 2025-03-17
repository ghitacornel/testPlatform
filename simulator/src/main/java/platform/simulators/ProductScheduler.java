package platform.simulators;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProductScheduler {

    private final ProductService service;

    @Scheduled(fixedRate = 500, initialDelay = 1000)
    void sell() {
        service.sell();
    }

    @Scheduled(fixedRate = 5000, initialDelay = 1000)
    void cancel() {
        service.cancel();
    }

}

