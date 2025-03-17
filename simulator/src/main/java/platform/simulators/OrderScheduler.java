package platform.simulators;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderScheduler {

    private final OrderService service;

    @Scheduled(fixedRate = 100, initialDelay = 1000)
    void create() {
        service.create();
    }

    @Scheduled(fixedRate = 500, initialDelay = 1000)
    void cancel() {
        service.cancel();
    }

    @Scheduled(fixedRate = 100, initialDelay = 1000)
    void complete() {
        service.complete();
    }

}

