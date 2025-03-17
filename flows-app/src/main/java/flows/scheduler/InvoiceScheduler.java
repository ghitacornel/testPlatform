package flows.scheduler;

import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class InvoiceScheduler {

    private final OrderClient orderClient;
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void sendCompletedToInvoice() {
        orderClient.findCompletedIds().forEach(id -> {
            orderClient.markAsSentToInvoice(id);
            jmsTemplate.convertAndSend("CompletedOrdersQueueName", id);
            log.info("Order sent to invoice {}", id);
        });
    }

}

