package flows.service;

import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productClient;
    private final ProductServiceHelper helper;

    @Async
    public void deleteCancelled() {
        productClient.findCancelledIds().forEach(helper::deleteCancelled);
    }

    @Async
    public void deleteConsumed() {
        productClient.findConsumedIds().forEach(helper::deleteConsumed);
    }

}

