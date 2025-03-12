package orders.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.service.RemoveOrdersSchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveOrdersScheduler {

    private final RemoveOrdersSchedulerService service;

    @Scheduled(fixedRate = 10000)
    private void removeCompletedOrders() {
        service.removeCompletedOrders();
    }

    @Scheduled(fixedRate = 10000)
    private void removeCancelledOrders() {
        service.removeCancelledOrders();
    }

}
