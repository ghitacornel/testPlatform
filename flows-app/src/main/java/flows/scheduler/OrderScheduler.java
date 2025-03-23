package flows.scheduler;

import flows.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderScheduler {

    private final OrderService orderService;

    @Scheduled(fixedRate = 1000)
    void deleteInvoiced() {
        orderService.deleteInvoiced();
    }

    @Scheduled(fixedRate = 1000)
    void deleteRejected() {
        orderService.deleteRejected();
    }

}

