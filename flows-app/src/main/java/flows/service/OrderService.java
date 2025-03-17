package flows.service;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import contracts.products.ProductBuyRequest;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderClient orderClient;
    private final ProductClient productClient;
    private final JmsTemplate jmsTemplate;
    private final OrderServiceHelper helper;

    public IdResponse createOrder(CreateOrderRequest request) {
        ProductBuyRequest productBuyRequest = ProductBuyRequest.builder()
                .clientId(request.getClientId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();

        productClient.buy(productBuyRequest);
        return orderClient.create(request);
    }

    public void completeOrder(Integer id) {
        orderClient.complete(id);
        jmsTemplate.convertAndSend("CompletedOrdersQueueName", id);
        log.info("Order completed {}", id);
    }

    public void cancelOrder(Integer id) {
        OrderDetailsResponse orderDetailsResponse = orderClient.findById(id);
        productClient.refill(orderDetailsResponse.getProductId(), orderDetailsResponse.getQuantity());
        orderClient.cancel(id);
        log.info("Order cancelled {}", id);
    }

    @Async
    public void sendCompletedToInvoice() {
        orderClient.findCompletedIds().forEach(helper::sendCompletedToInvoice);
    }

    @Async
    public void deleteInvoiced() {
        orderClient.findInvoicedIds().forEach(helper::deleteInvoiced);
    }

}
