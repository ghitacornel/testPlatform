package platform.simulators;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.products.ProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.clients.OrderClient;
import platform.clients.ProductClient;
import platform.utils.GenerateUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final Random random = new Random();

    private final ClientClient clientClient;
    private final ProductClient productClient;
    private final OrderClient orderClient;
    private final FlowsClient flowsClient;

    private final AsyncCache<Integer, Object> cacheCancelled = Caffeine.newBuilder()
            .expireAfterWrite(Duration.of(1, ChronoUnit.MINUTES))
            .buildAsync();

    @Async
    public void create() {
        Integer clientId = GenerateUtils.random(clientClient.findActiveIds(), random);
        if (clientId == null) {
            log.warn("no clients available");
            return;
        }

        ProductDetailsResponse productDetailsResponse = GenerateUtils.random(productClient.findAllActive(), random);
        if (productDetailsResponse == null) {
            log.warn("no products available");
            return;
        }

        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .clientId(clientId)
                .productId(productDetailsResponse.getId())
                .quantity(generateRandomQuantity(productDetailsResponse))
                .build();

        IdResponse idResponse = flowsClient.createOrder(createOrderRequest);
        if (idResponse == null) {
            log.warn("null response for create order");
            return;
        }
        Integer id = idResponse.getId();
        log.info("Order created {}", id);

    }

    @Async
    public void cancel() {
        Integer id = GenerateUtils.random(orderClient.findNewIds(), random, cacheCancelled);
        if (id == null) {
            log.warn("No order to cancel");
            return;
        }
        flowsClient.cancelOrder(id);
        log.info("Order cancelled {}", id);
    }

    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

