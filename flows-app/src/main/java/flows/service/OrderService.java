package flows.service;

import commons.exceptions.BusinessException;
import commons.exceptions.RestTechnicalException;
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

        IdResponse idResponse = orderClient.create(request);

        try {
            jmsTemplate.convertAndSend("ToBeConfirmedOrdersQueueName", idResponse.getId());
        } catch (Exception e) {
            log.error("error sending order confirmation {}", idResponse.getId(), e);
        }
        return idResponse;
    }

    public void cancelOrder(Integer id) {
        OrderDetailsResponse orderDetailsResponse = orderClient.findById(id);
        try {
            productClient.refill(orderDetailsResponse.getProductId(), orderDetailsResponse.getQuantity());
        } catch (RestTechnicalException e) {
            log.error("error cancelling order {} {}", id, e.getMessage());
        }
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

    @Async
    public void deleteRejected() {
        orderClient.findRejectedIds().forEach(helper::deleteRejected);
    }

    public void confirm(Integer id) {

        OrderDetailsResponse orderDetails;
        try {
            orderDetails = orderClient.findById(id);
        } catch (BusinessException e) {
            log.error("Error finding order {} {}", id, e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Error finding order {}", id, e);
            return;
        }

        ProductBuyRequest productBuyRequest = ProductBuyRequest.builder()
                .clientId(orderDetails.getClientId())
                .productId(orderDetails.getProductId())
                .quantity(orderDetails.getQuantity())
                .build();
        try {
            productClient.buy(productBuyRequest);
        } catch (RestTechnicalException e) {
            log.error("Error reserving product for order {} {}", id, e.getMessage());
            orderClient.reject(id);
            return;
        } catch (BusinessException e) {
            log.warn("problem reserving product for order {} {}", id, e.getMessage());
            orderClient.reject(id);
            return;
        } catch (Exception e) {
            log.error("Error reserving product for order {}", id, e);
            orderClient.reject(id);
            return;
        }

        try {
            orderClient.complete(id);
        } catch (BusinessException e) {
            log.warn("problem marking order as completed {} {}", id, e.getMessage());
            try {
                orderClient.reject(id);
            } catch (BusinessException ex) {
                log.error("Error marking order as rejected {} {}", id, ex.getMessage());
                return;
            }
            return;
        } catch (Exception e) {
            log.error("Error marking order as completed {}", id, e);
            return;
        }

        try {
            jmsTemplate.convertAndSend("CompletedOrdersQueueName", id);
        } catch (Exception e) {
            log.error("Error sending order id to CompletedOrdersQueueName {}", id, e);
            return;
        }
        log.info("Order completed {}", id);
    }

}
