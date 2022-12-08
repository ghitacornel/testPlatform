package products.clients.order;

import commons.exceptions.BusinessException;
import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import products.clients.RESTClient;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final RESTClient restClient;

    public IdResponse createOrder(CreateOrderRequest request) {
        return restClient.orders()
                .post()
                .uri("/order")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(IdResponse.class)
                .doOnError(throwable -> {
                    throw new BusinessException("Exception creating order for request " + request, throwable);
                })
                .block();
    }

    public Boolean existsByProductId(Integer id) {
        return restClient.orders()
                .get()
                .uri("/order/product/" + id)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

}
