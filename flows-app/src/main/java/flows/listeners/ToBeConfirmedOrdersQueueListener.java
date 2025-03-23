package flows.listeners;

import flows.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ToBeConfirmedOrdersQueueListener {

    private final OrderService orderService;

    @JmsListener(destination = "ToBeConfirmedOrdersQueueName", concurrency = "10")
    public void listenerForToBeConfirmedOrdersQueueName(Integer id) {
        orderService.confirm(id);
    }

}

