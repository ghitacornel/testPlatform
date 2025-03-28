package flows.service;

import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productClient;
    private final ProductServiceHelper helper;

    public void deleteCancelled() {
        productClient.findCancelledIds().forEach(helper::delete);
    }

    public void deleteConsumed() {
        productClient.findConsumedIds().forEach(helper::delete);
    }

}

