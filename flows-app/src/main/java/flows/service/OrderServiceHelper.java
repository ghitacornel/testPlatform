package flows.service;

import commons.exceptions.ResourceNotFound;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class OrderServiceHelper {

    @Value(value = "${topic.completedOrders}")
    private String completedOrdersTopic;

    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    void sendCompletedToInvoice(Integer id) {
        kafkaTemplate.send(completedOrdersTopic, String.valueOf(id));

        try {
            orderClient.markAsSentToInvoice(id);
            log.info("Order sent to invoice {}", id);
        } catch (ResourceNotFound e) {
            log.error("Order not found {} for sending to invoice", id);
        }
    }

    @Async
    void deleteInvoiced(Integer id) {
        if (invoiceClient.existsByOrderId(id)) {
            return;
        }

        try {
            orderClient.delete(id);
            log.info("Invoiced order deleted {}", id);
        } catch (ResourceNotFound e) {
            log.error("Order not found {} for delete", id);
        }
    }

    @Async
    void deleteRejected(Integer id) {
        try {
            orderClient.delete(id);
            log.info("Rejected order deleted {}", id);
        } catch (ResourceNotFound e) {
            log.error("Rejected order not found {} for delete", id);
        }
    }

}
