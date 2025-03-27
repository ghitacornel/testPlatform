package flows.listeners;

import flows.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ToBeConfirmedOrdersQueueListener {

    private final OrderService orderService;

    @KafkaListener(topics = "ToBeConfirmedOrdersTopic", containerFactory = "kafkaListenerDataModelContainerFactory")
    public void listenerForToBeConfirmedOrdersQueueName(Integer id) {
        orderService.confirm(id);
    }

}

