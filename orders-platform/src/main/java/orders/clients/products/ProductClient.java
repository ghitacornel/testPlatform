package orders.clients.products;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RESTClient restClient;

    public ProductDto findById(Integer id) {
        return restClient.products()
                .get()
                .uri("/product/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BusinessException("Product not found for id " + id)))
                .bodyToMono(ProductDto.class)
                .block();
    }
}
