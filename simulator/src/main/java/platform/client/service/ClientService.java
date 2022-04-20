package platform.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.client.model.Client;
import platform.common.RESTClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RESTClient restClient;

    public List<Client> findAll() {
        return restClient.clients()
                .get()
                .uri("/client/all")
                .retrieve()
                .bodyToFlux(Client.class)
                .collectList()
                .block();
    }

    public Client register(Client item) {
        return restClient.clients()
                .post()
                .uri("/client/register")
                .bodyValue(item)
                .retrieve()
                .bodyToMono(Client.class)
                .block();
    }

    public void unregister(Client item) {
        restClient.clients()
                .delete()
                .uri("/client/" + item.getName())
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
