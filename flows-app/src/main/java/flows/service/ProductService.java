package flows.service;

import commons.exceptions.ResourceNotFound;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productClient;
    private final ProductServiceHelper helper;
    private final OrderClient orderClient;

    public void deleteCancelled() {
        productClient.findCancelledIds().forEach(helper::delete);
    }

    public void deleteConsumed() {
        productClient.findConsumedIds().forEach(helper::delete);
    }

    public void cancelProduct(Integer id) {

        try {
            productClient.cancel(id);
            log.info("product cancelled {}", id);
        } catch (ResourceNotFound e) {
            log.error("to be cancelled product not found {}", id);
        }

        try {
            orderClient.findNewIdsForProductId(id).forEach(orderClient::cancel);
            log.info("cancelled orders for product {}", id);
        } catch (Exception e) {
            log.error("cancelled orders for product {}", id, e);
        }
    }
}

