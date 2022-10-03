package platform.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import platform.common.RESTClient;
import platform.order.model.Order;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RESTClient restClient;

    public List<Order> findAll() {
        return restClient.orders()
                .get()
                .uri("/order")
                .retrieve()
                .bodyToFlux(Order.class)
                .collectList()
                .block();
    }

    public void complete(Order item) {
        restClient.orders()
                .patch()
                .uri("/order/complete/" + item.getId())
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
