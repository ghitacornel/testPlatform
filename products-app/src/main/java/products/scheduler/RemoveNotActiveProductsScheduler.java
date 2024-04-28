package products.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import products.repository.ProductRepository;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveNotActiveProductsScheduler {

    private final ProductRepository productRepository;

    @Scheduled(fixedRate = 10000)
    private void removeNotActiveProducts() {
        productRepository.findAllConsumed().forEach(product -> {
            log.info("Removing consumed product {}", product);
            productRepository.delete(product);
        });
        productRepository.findAllCancelled().forEach(product -> {
            log.info("Removing cancelled product {}", product);
            productRepository.delete(product);
        });
    }

}
