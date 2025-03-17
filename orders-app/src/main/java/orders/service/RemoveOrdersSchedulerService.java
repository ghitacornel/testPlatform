package orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveOrdersSchedulerService {

    private final OrderRepository repository;

    public void removeCancelledOrders() {
        repository.deleteAllByStatusCancelled();
    }

}
