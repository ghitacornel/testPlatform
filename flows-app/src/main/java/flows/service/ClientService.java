package flows.service;

import commons.exceptions.ResourceNotFound;
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
        clientClient.retiring(id);
        deleteRetiringClient(id);
    }

    public void deleteRetiringClient(Integer id) {

        orderClient.findNewIdsForClientId(id).forEach(orderClient::cancel);

        if (orderClient.existsByClientId(id)) {
            return;
        }
        if (invoiceClient.existsByClientId(id)) {
            return;
        }

        try {
            clientClient.retired(id);
            log.info("retired client {}", id);
        } catch (ResourceNotFound e) {
            log.warn("Client not found for retire {}", id);
        }

    }

}

