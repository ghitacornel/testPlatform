package orders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import orders.repository.OrderRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdersSchedulerService {

    private final OrderRepository repository;

    @Async
    public void removeCancelledOrders() {
        repository.deleteAllByStatusCancelled();
    }

    @Async
    public void removeInvoicedOrders() {
        repository.deleteAllByStatusInvoiced();
    }

}
