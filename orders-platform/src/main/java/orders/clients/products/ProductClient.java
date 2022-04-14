package orders.clients.products;

import lombok.RequiredArgsConstructor;
import orders.clients.RESTClient;
import orders.clients.company.CompanyDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RESTClient restClient;

    public ProductDto findById(Integer id) {
        return restClient.products()
                .get()
                .uri("/product/" + id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }
}
