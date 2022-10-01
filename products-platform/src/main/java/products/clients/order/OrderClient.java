package products.clients.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import products.clients.RESTClient;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final RESTClient restClient;

    public OrderDto createOrder(CreateOrderRequest request) {
        return restClient.orders()
                .post()
                .uri("/order" )
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OrderDto.class)
                .block();
    }
}
