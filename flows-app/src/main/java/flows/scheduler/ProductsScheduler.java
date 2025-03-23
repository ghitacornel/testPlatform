package flows.scheduler;

import flows.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProductsScheduler {

    private final ProductService productService;

    @Scheduled(fixedRate = 10000)
    void deleteCancelled() {
        productService.deleteCancelled();
    }

    @Scheduled(fixedRate = 10000)
    void deleteConsumed() {
        productService.deleteConsumed();
    }

}

