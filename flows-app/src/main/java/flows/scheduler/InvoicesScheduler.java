package flows.scheduler;

import flows.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InvoicesScheduler {

    private final OrderService orderService;

    @Scheduled(fixedRate = 10000)
    void sendCompletedToInvoice() {
        orderService.sendCompletedToInvoice();
    }

    @Scheduled(fixedRate = 10000)
    void checkSentToInvoice() {
        orderService.checkSentToInvoice();
    }

}

