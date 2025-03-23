package flows.listeners;

import flows.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CompleteOrdersQueueListener {

    private final InvoiceService invoiceService;

    @JmsListener(destination = "CompletedOrdersQueueName")
    public void listenerForCompletedOrdersQueueName(Integer id) {
        invoiceService.complete(id);
    }

}

