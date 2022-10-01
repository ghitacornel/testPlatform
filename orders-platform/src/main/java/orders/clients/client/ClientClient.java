package orders.clients.client;

import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientClient {

    private final RESTClient restClient;

    public ClientDto findById(Integer id) {
        return restClient.clients()
                .get()
                .uri("/client/" + id)
                .retrieve()
                .bodyToMono(ClientDto.class)
                .block();
    }
}
