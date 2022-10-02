package orders.scheduler;

import lombok.RequiredArgsConstructor;
import orders.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteCompletedOrdersScheduler {

    private final OrderRepository repository;

    @Scheduled(fixedRate = 10000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCompleted() {
        repository.deleteCompleted();
    }

}
