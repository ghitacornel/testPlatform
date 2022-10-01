package orders.clients.client;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientClient {

    private final RESTClient restClient;

    public ClientDto findById(Integer id) {
        return restClient.clients()
                .get()
                .uri("/client/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BusinessException("Client not found for id " + id)))
                .bodyToMono(ClientDto.class)
                .block();
    }
}
