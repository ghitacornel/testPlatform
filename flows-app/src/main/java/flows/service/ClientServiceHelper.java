package flows.service;

import commons.exceptions.ResourceNotFound;
import flows.clients.ClientClient;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ClientServiceHelper {

    private final ClientClient clientClient;
    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    @Async
    void deleteRetired(Integer id) {
        if (orderClient.existsByClientId(id)) {
            return;
        }
        if (invoiceClient.existsByClientId(id)) {
            return;
        }
        try {
            clientClient.delete(id);
            log.info("Retired client deleted {}", id);
        } catch (ResourceNotFound e) {
            log.warn("Retired client not found {}", id);
        }
    }

}

