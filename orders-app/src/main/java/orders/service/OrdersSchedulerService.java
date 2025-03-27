package orders.service;

import contracts.orders.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdersSchedulerService {

    private final OrderRepository repository;

    public void removeCancelledOrders() {
        repository.deleteByStatus(Status.CANCELLED);
    }

    public void removeRejectedOrders() {
        repository.deleteByStatus(Status.REJECTED);
    }

    public void removeInvoicedOrders() {
        repository.deleteByStatus(Status.INVOICED);
    }

}
