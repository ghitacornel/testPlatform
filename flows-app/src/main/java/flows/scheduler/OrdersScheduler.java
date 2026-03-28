package flows.scheduler;

import flows.clients.OrderClient;
import flows.service.InvoiceService;
import flows.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrdersScheduler {

    private final OrderClient orderClient;
    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @Scheduled(fixedRate = 10000)
    void sendNewToConfirm() {
        orderClient.findNewIds().forEach(orderService::confirm);
    }

    @Scheduled(fixedRate = 10000)
    void sendConfirmedToInvoice() {
        orderClient.findCompletedIds().forEach(invoiceService::complete);
    }

}

