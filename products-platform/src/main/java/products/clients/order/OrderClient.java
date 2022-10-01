package products.clients.order;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import products.clients.RESTClient;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final RESTClient restClient;

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        return restClient.orders()
                .post()
                .uri("/order")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CreateOrderResponse.class)
                .doOnError(throwable -> {
                    throw new BusinessException("Exception creating order for request " + request, throwable);
                })
                .block();
    }
}
