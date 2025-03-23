package flows.scheduler;

import flows.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InvoiceScheduler {

    private final OrderService orderService;

    @Scheduled(fixedRate = 1000)
    void sendCompletedToInvoice() {
        orderService.sendCompletedToInvoice();
    }

    @Scheduled(fixedRate = 1000)
    void checkSentToInvoice() {
        orderService.checkSentToInvoice();
    }

}

