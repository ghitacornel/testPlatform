package flows.service;

import commons.exceptions.BusinessException;
import commons.exceptions.ResourceNotFound;
import commons.exceptions.RestTechnicalException;
import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import contracts.orders.OrderRejectRequest;
import contracts.products.ProductBuyRequest;
import flows.clients.OrderClient;
import flows.clients.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Value(value = "${kafka.topic.completedOrders}")
    private String completedOrdersTopic;
    @Value(value = "${kafka.topic.toBeConfirmedOrders}")
    private String toBeConfirmedOrdersTopic;

    private final OrderClient orderClient;
    private final ProductClient productClient;
    private final OrderServiceHelper helper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public IdResponse createOrder(CreateOrderRequest request) {

        IdResponse idResponse;
        try {
            idResponse = orderClient.create(request);
        } catch (Exception e) {
            throw new BusinessException("error creating order " + request, e);
        }

        kafkaTemplate.send(toBeConfirmedOrdersTopic, String.valueOf(idResponse.getId()));
        return idResponse;
    }

    public void cancelOrder(Integer id) {

        OrderDetailsResponse orderDetails;
        try {
            orderDetails = orderClient.findById(id);
        } catch (ResourceNotFound e) {
            log.warn("Order not found for cancellation {}", id);
            return;
        }

        try {
            productClient.refill(orderDetails.getProductId(), orderDetails.getQuantity());
        } catch (RestTechnicalException e) {
            throw new BusinessException("Error refilling product " + orderDetails, e);
        }

        orderClient.cancel(id);
        log.info("Order cancelled {}", id);
    }

    public void sendCompletedToInvoice() {
        orderClient.findCompletedIds().forEach(helper::sendCompletedToInvoice);
    }

    public void checkSentToInvoice() {
        orderClient.findSentIds().forEach(helper::sendCompletedToInvoice);
    }

    @Async
    public void confirm(Integer id) {

        OrderDetailsResponse orderDetails;
        try {
            orderDetails = orderClient.findById(id);
        } catch (ResourceNotFound e) {
            log.error("Order not found for confirmation {}", id);
            return;
        }

        ProductBuyRequest productBuyRequest = ProductBuyRequest.builder()
                .clientId(orderDetails.getClientId())
                .productId(orderDetails.getProductId())
                .quantity(orderDetails.getQuantity())
                .build();
        try {
            productClient.buy(productBuyRequest);
        } catch (RestTechnicalException | BusinessException e) {
            rejectOrder(id, e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Error reserving product for order {}", id, e);
            rejectOrder(id, e.getMessage());
            return;
        }

        kafkaTemplate.send(completedOrdersTopic, String.valueOf(id));

        try {
            orderClient.complete(id);
            log.info("Order completed {}", id);
        } catch (ResourceNotFound e) {
            log.error("Order not found for marking it as completed {}", id);
            rejectOrder(id, e.getMessage());
        } catch (Exception e) {
            log.error("Error marking order as completed {}", id, e);
        }
    }

    private void rejectOrder(Integer id, String message) {
        try {
            orderClient.reject(new OrderRejectRequest(id, message));
            log.warn("Order rejected {} {}", id, message);
        } catch (ResourceNotFound e) {
            log.error("Order not found for rejection {} {}", id, message);
        } catch (RestTechnicalException e) {
            log.error("Error marking order as rejected {} {} {}", id, message, e.getMessage());
        } catch (Exception e) {
            log.error("Error marking order as rejected {} {}", id, message, e);
        }
    }

}
