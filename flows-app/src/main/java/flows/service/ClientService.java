package flows.service;

import flows.clients.ClientClient;
import flows.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientClient clientClient;
    private final OrderClient orderClient;

    public void deleteClient(Integer id) {
        clientClient.unregister(id);
        orderClient.findAllNewForClientId(id)
                .forEach(orderDetailsResponse -> orderClient.cancel(orderDetailsResponse.getId()));
    }

}

