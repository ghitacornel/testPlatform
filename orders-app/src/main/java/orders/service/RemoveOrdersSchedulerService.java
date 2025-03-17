package orders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RemoveOrdersSchedulerService {

    private final OrderRepository repository;

    public void removeCancelledOrders() {
        repository.deleteAllByStatusCancelled();
    }

    public void removeInvoicedOrders() {
        repository.deleteAllByStatusInvoiced();
    }

}
