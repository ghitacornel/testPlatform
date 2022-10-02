package orders.schedulers;

import lombok.RequiredArgsConstructor;
import orders.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCompletedOrdersScheduler {

    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 1000)
    public void deleteCompletedOrders() {
        orderRepository.deleteCompletedOrders();
    }

}
