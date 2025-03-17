package orders.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.service.RemoveOrdersSchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class OrdersScheduler {

    private final RemoveOrdersSchedulerService service;

    @Scheduled(fixedRate = 10000)
    void removeCancelledOrders() {
        service.removeCancelledOrders();
    }

}
