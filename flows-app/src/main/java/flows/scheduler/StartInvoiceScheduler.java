package flows.scheduler;

import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StartInvoiceScheduler {

    private final OrderClient orderClient;
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    public void configure() {
        orderClient.findCompletedIds().forEach(id -> {
            orderClient.markAsSentToInvoice(id);
            jmsTemplate.convertAndSend("CompletedOrdersQueueName", id);
        });
    }

}

