package orders.scheduler;

import lombok.RequiredArgsConstructor;
import orders.service.RemoveOrdersSchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrdersScheduler {

    private final RemoveOrdersSchedulerService service;

    @Scheduled(fixedRate = 10000)
    void removeCancelledOrders() {
        service.removeCancelledOrders();
    }

    @Scheduled(fixedRate = 10000)
    void removeInvoicedOrders() {
        service.removeInvoicedOrders();
    }

}
