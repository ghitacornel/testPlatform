package flows.listeners;

import flows.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CompleteOrdersQueueListener {

    private final InvoiceService invoiceService;

    @KafkaListener(topics = "CompletedOrdersTopic", containerFactory = "kafkaListenerDataModelContainerFactory")
    public void listenerForCompletedOrdersQueueName(Integer id) {
        invoiceService.complete(id);
    }

}

