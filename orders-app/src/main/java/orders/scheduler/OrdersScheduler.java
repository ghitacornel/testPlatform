package orders.scheduler;

import contracts.orders.Status;
import lombok.RequiredArgsConstructor;
import orders.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
class OrdersScheduler {

    private final OrderRepository repository;

    @Scheduled(fixedRate = 10000)
    void removeCancelled() {
        repository.deleteByStatus(Status.CANCELLED);
    }

    @Scheduled(fixedRate = 10000)
    void removeRejected() {
        repository.deleteByStatus(Status.REJECTED);
    }

    @Scheduled(fixedRate = 10000)
    void removeInvoiced() {
        repository.deleteByStatus(Status.INVOICED);
    }

}
