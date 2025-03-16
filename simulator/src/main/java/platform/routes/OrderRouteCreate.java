package platform.routes;

import commons.exceptions.RestTechnicalException;
import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.products.ProductDetailsResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.clients.ProductClient;
import platform.utils.GenerateUtils;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrderRouteCreate extends RouteBuilder {

    private final Random random = new Random();

    private final ClientClient clientClient;
    private final ProductClient productClient;
    private final FlowsClient flowsClient;

    @Override
    public void configure() {

        for (int i = 0; i < 5; i++) {
            from("timer://simpleTimer?period=100&delay=1000")
                    .routeId("create-order-route-timer-" + i)
                    .multicast()
                    .parallelProcessing()
                    .to("direct:create-order-route-" + i)
            ;

            from("direct:create-order-route-" + i)
                    .routeId("create-order-route-" + i)
                    .setBody(exchange -> {

                        Integer clientId = GenerateUtils.random(clientClient.findActiveIds(), random);
                        if (clientId == null) {
                            log.warn("no clients available");
                            return null;
                        }
                        ProductDetailsResponse productDetailsResponse = GenerateUtils.random(productClient.findAllActive(), random);
                        if (productDetailsResponse == null) {
                            log.warn("no products available");
                            return null;
                        }
                        return CreateOrderRequest.builder()
                                .clientId(clientId)
                                .productId(productDetailsResponse.getId())
                                .quantity(generateRandomQuantity(productDetailsResponse))
                                .build();
                    })
                    .choice()
                    .when(body().isNull()).log("no order created")
                    .otherwise()
                    .process(exchange -> {
                        try {
                            IdResponse idResponse = flowsClient.createOrder(exchange.getMessage().getBody(CreateOrderRequest.class));
                            if (idResponse == null) {
                                log.warn("null response for create order");
                                return;
                            }
                            Integer id = idResponse.getId();
                            log.info("Order created {}", id);
                        } catch (RestTechnicalException | FeignException e) {
                            log.error(e.getMessage());
                        }
                    })
                    .endChoice()
                    .end();
        }

    }

    private int generateRandomQuantity(ProductDetailsResponse productDetailsResponse) {
        int quantity = random.nextInt(productDetailsResponse.getQuantity());
        if (quantity == 0) quantity++;
        return quantity;
    }

}

