package orders.controller;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderContract;
import contracts.orders.OrderDetailsResponse;
import lombok.RequiredArgsConstructor;
import orders.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController implements OrderContract {

    private final OrderService service;

    public List<OrderDetailsResponse> findAllNew() {
        return service.findAllNew();
    }

    public List<Integer> findCompletedIds() {
        return service.findCompletedIds();
    }

    public List<Integer> findInvoicedIds() {
        return service.findInvoicedIds();
    }

    public List<Integer> findNewIds() {
        return service.findNewIds();
    }

    public List<Integer> findRejectedIds() {
        return service.findRejectedIds();
    }

    public List<OrderDetailsResponse> findAllNewForClientId(Integer id) {
        return service.findAllNewForClientId(id);
    }

    public List<OrderDetailsResponse> findAllNewForProductId(Integer id) {
        return service.findAllNewForProductId(id);
    }

    public long countAllNew() {
        return service.countAllNew();
    }

    public OrderDetailsResponse findById(Integer id) {
        return service.findById(id);
    }

    public boolean existsByProductId(Integer id) {
        return service.existsByProductId(id);
    }

    public boolean existsByClientId(Integer id) {
        return service.existsByClientId(id);
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

    public void reject(Integer id, String reason) {
        service.reject(id, reason);
    }

    public void delete(Integer id) {
        service.delete(id);
    }

}
