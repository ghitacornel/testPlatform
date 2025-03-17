package platform.simulators;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.products.ProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.clients.ProductClient;
import platform.utils.GenerateUtils;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
class OrderRouteCreate {

    private final Random random = new Random();

    private final ClientClient clientClient;
    private final ProductClient productClient;
    private final FlowsClient flowsClient;

    @Scheduled(fixedRate = 100, initialDelay = 1000)
    void simulate1() {
        simulate();
    }

    @Scheduled(fixedRate = 100, initialDelay = 1020)
    void simulate2() {
        simulate();
    }

    @Scheduled(fixedRate = 100, initialDelay = 1040)
    void simulate3() {
        simulate();
    }

    @Scheduled(fixedRate = 100, initialDelay = 1060)
    void simulate4() {
        simulate();
    }

    @Scheduled(fixedRate = 100, initialDelay = 1080)
    void simulate5() {
        simulate();
    }

    private void simulate() {

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

        try {
            IdResponse idResponse = flowsClient.createOrder(createOrderRequest);
            if (idResponse == null) {
                log.warn("null response for create order");
                return;
            }
            Integer id = idResponse.getId();
            log.info("Order created {}", id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

