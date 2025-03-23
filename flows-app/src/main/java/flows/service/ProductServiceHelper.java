package flows.service;

import commons.exceptions.ResourceNotFound;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ProductServiceHelper {

    private final ProductClient productClient;
    private final InvoiceClient invoiceClient;
    private final OrderClient orderClient;

    @Async
    void deleteCancelled(Integer id) {
        if (orderClient.existsByProductId(id)) {
            return;
        }
        if (invoiceClient.existsByProductId(id)) {
            return;
        }
        try {
            productClient.delete(id);
            log.info("Cancelled product deleted {}", id);
        } catch (ResourceNotFound e) {
            log.error("Cancelled product not found {} for delete", id);
        }
    }

    @Async
    void deleteConsumed(Integer id) {
        if (orderClient.existsByProductId(id)) {
            return;
        }
        if (invoiceClient.existsByProductId(id)) {
            return;
        }
        try {
            productClient.delete(id);
            log.info("Consumed product deleted {}", id);
        } catch (ResourceNotFound e) {
            log.error("Consumed product not found {} for delete", id);
        }
    }

}

