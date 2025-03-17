package flows.scheduler;

import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ProductsScheduler {

    private final ProductClient productClient;
    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void deleteCancelled() {
        productClient.findCancelledIds().forEach(id -> {
            if (orderClient.existsByProductId(id)) {
                return;
            }
            if (invoiceClient.existsByProductId(id)) {
                return;
            }
            productClient.delete(id);
            log.info("Cancelled product deleted {}", id);
        });
    }

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void deleteConsumed() {
        productClient.findConsumedIds().forEach(id -> {
            if (orderClient.existsByProductId(id)) {
                return;
            }
            if (invoiceClient.existsByProductId(id)) {
                return;
            }
            productClient.delete(id);
            log.info("Consumed product deleted {}", id);
        });
    }

}

