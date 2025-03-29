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

    @Value(value = "${topic.completedOrders}")
    private String completedOrdersTopic;
    @Value(value = "${topic.toBeConfirmedOrders}")
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

        try {
            kafkaTemplate.send(toBeConfirmedOrdersTopic, String.valueOf(idResponse.getId()));
        } catch (Exception e) {
            log.error("error sending order confirmation {}", idResponse.getId(), e);
            throw new BusinessException("error sending order confirmation " + request, e);
        }
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

        try {
            orderClient.cancel(id);
            log.info("Order cancelled {}", id);
        } catch (ResourceNotFound e) {
            log.error("Order not found for cancelling {}", id);
        }

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

        try {
            kafkaTemplate.send(completedOrdersTopic, String.valueOf(id));
        } catch (Exception e) {
            throw new BusinessException("Error sending order " + id + " to CompletedOrdersQueueName", e);
        }

        try {
            orderClient.complete(id);
        } catch (ResourceNotFound e) {
            log.error("Order not found for marking it as completed {}", id);
            rejectOrder(id, e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Error marking order as completed {}", id, e);
            return;
        }

        log.info("Order completed {}", id);
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
