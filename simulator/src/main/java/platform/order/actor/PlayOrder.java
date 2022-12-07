package platform.order.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.common.AbstractActor;
import platform.order.model.Order;
import platform.order.service.OrderService;
import platform.random.RandomDataFetchService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayOrder extends AbstractActor {

    private static final int MINIMUM = 50;
    private final OrderService orderService;
    private final RandomDataFetchService randomDataFetchService;

    @Scheduled(fixedRate = 50)
    public void complete() {
        if (orderService.count() < MINIMUM) {
            return;
        }

        Order order = randomDataFetchService.findRandomOrder();
        if (order == null) {
            return;
        }
        orderService.complete(order);
        log.info("order completed " + order);
    }

}
