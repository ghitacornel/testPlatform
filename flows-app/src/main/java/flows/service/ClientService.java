package flows.service;

import commons.exceptions.ResourceNotFound;
import flows.clients.ClientClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientClient clientClient;
    private final OrderClient orderClient;
    private final ClientServiceHelper helper;

    public void deleteClient(Integer id) {

        try {
            clientClient.retire(id);
        } catch (ResourceNotFound e) {
            log.warn("Client not found {}", id);
            return;
        }

        orderClient.findAllNewForClientId(id)
                .forEach(orderDetailsResponse -> orderClient.cancel(orderDetailsResponse.getId()));
    }

    @Async
    public void deleteRetired() {
        clientClient.findRetiredIds().forEach(helper::deleteRetired);
    }

}

