package flows.scheduler;

import flows.clients.ClientClient;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientsScheduler {

    private final ClientClient clientClient;
    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void deleteRetired() {
        clientClient.findRetiredIds().forEach(id -> {
            if (orderClient.existsByClientId(id)) {
                return;
            }
            if (invoiceClient.existsByClientId(id)) {
                return;
            }
            clientClient.delete(id);
            log.info("Retired client deleted {}", id);
        });
    }

}

