package products.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import products.clients.order.OrderClient;
import products.repository.ProductRepository;
import products.repository.entity.Product;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteNotActiveProductsScheduler {

    private final ProductRepository repository;
    private final OrderClient orderClient;

    @Scheduled(fixedRate = 10000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteNotActiveProducts() {
        List<Product> products = repository.findAllNotActive();
        for (Product product : products) {
            if (!orderClient.existsByProductId(product.getId())) {
                repository.delete(product);
                log.debug("deleted product " + product);
            }
        }
    }

}
