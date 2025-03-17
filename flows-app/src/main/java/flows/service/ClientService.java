package flows.service;

import flows.clients.ClientClient;
import flows.clients.InvoiceClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientClient clientClient;
    private final OrderClient orderClient;
    private final InvoiceClient invoiceClient;

    public void deleteClient(Integer id) {
        clientClient.unregister(id);
        orderClient.findAllNewForClientId(id)
                .forEach(orderDetailsResponse -> orderClient.cancel(orderDetailsResponse.getId()));
    }

    public void deleteRetired() {
        clientClient.findRetiredIds()
                .forEach(id -> {
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

