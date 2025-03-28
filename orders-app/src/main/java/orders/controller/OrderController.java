package orders.controller;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderContract;
import contracts.orders.OrderDetailsResponse;
import contracts.orders.OrderRejectRequest;
import lombok.RequiredArgsConstructor;
import orders.service.OrderSearchService;
import orders.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController implements OrderContract {

    private final OrderService service;
    private final OrderSearchService searchService;

    public List<OrderDetailsResponse> findAllNew() {
        return searchService.findAllNew();
    }

    public List<Integer> findCompletedIds() {
        return searchService.findCompletedIds();
    }

    public List<Integer> findSentIds() {
        return searchService.findSentIds();
    }

    public List<Integer> findInvoicedIds() {
        return searchService.findInvoicedIds();
    }

    public List<Integer> findNewIds() {
        return searchService.findNewIds();
    }

    public List<Integer> findRejectedIds() {
        return searchService.findRejectedIds();
    }

    public List<Integer> findNewIdsForClientId(Integer id) {
        return searchService.findAllNewForClientId(id);
    }

    public List<Integer> findNewIdsForProductId(Integer id) {
        return searchService.findAllNewForProductId(id);
    }

    public long countAllNew() {
        return searchService.countAllNew();
    }

    public OrderDetailsResponse findById(Integer id) {
        return searchService.findById(id);
    }

    public boolean existsByProductId(Integer id) {
        return searchService.existsByProductId(id);
    }

    public boolean existsByClientId(Integer id) {
        return searchService.existsByClientId(id);
    }

    public IdResponse create(CreateOrderRequest request) {
        return service.create(request);
    }

    public void complete(Integer id) {
        service.complete(id);
    }

    public void invoice(Integer id) {
        service.invoice(id);
    }

    public void markAsSentToInvoice(Integer id) {
        service.markAsSentToInvoice(id);
    }

    public void cancel(Integer id) {
        service.cancel(id);
    }

    public void cancelByProductId(Integer id) {
        service.cancelByProductId(id);
    }

    public void reject(OrderRejectRequest request) {
        service.reject(request.getId(), request.getReason());
    }

}
