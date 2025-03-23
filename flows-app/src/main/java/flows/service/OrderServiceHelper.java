package flows.service;

import commons.exceptions.BusinessException;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class OrderServiceHelper {

    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;
    private final JmsTemplate jmsTemplate;

    @Async
    void sendCompletedToInvoice(Integer id) {
        jmsTemplate.convertAndSend("CompletedOrdersQueueName", id);
        try {
            orderClient.markAsSentToInvoice(id);
        } catch (BusinessException e) {
            log.error("Business error marking order as rejected {} {}", id, e.getMessage());
            return;
        }
        log.info("Order sent to invoice {}", id);
    }

    @Async
    void deleteInvoiced(Integer id) {
        if (invoiceClient.existsByOrderId(id)) {
            return;
        }
        orderClient.delete(id);
        log.info("Order invoiced deleted {}", id);
    }

    @Async
    void deleteRejected(Integer id) {
        orderClient.delete(id);
        log.info("Order rejected deleted {}", id);
    }

}
