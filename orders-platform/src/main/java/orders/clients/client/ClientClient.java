package orders.clients.client;

import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientClient {

    private final RESTClient restClient;

    public ClientDto findByName(String name) {
        return restClient.clients()
                .get()
                .uri("/client/" + name)
                .retrieve()
                .bodyToMono(ClientDto.class)
                .block();
    }
}
