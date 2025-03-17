package flows.scheduler;

import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class OrderScheduler {

    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void deleteInvoiced() {
        orderClient.findInvoicedIds().forEach(id -> {
            if (invoiceClient.existsByOrderId(id)) {
                return;
            }
            orderClient.delete(id);
            log.info("Order invoiced deleted {}", id);
        });
    }
}

