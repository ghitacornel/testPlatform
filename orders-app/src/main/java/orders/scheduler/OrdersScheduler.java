package orders.scheduler;

import lombok.RequiredArgsConstructor;
import orders.service.OrdersSchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrdersScheduler {

    private final OrdersSchedulerService service;

    @Scheduled(fixedRate = 10000)
    void removeCancelledOrders() {
        service.removeCancelledOrders();
    }

    @Scheduled(fixedRate = 10000)
    void removeRejectedOrders() {
        service.removeRejectedOrders();
    }

    @Scheduled(fixedRate = 10000)
    void removeInvoicedOrders() {
        service.removeInvoicedOrders();
    }

}
