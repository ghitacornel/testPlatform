package products.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import products.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class DeleteCancelledProductsScheduler {

    private final ProductRepository repository;

    @Scheduled(fixedRate = 10000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCancelled() {
        repository.deleteCancelled();
    }

}
