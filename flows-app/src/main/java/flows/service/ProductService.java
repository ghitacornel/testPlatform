package flows.service;

import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productClient;
    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;

    @Async
    public void deleteCancelled() {
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

    @Async
    public void deleteConsumed() {
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

