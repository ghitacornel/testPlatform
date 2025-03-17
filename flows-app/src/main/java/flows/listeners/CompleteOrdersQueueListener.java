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
    public void listenerForQueue1(Integer id) {
        invoiceService.complete(id);
    }

}

