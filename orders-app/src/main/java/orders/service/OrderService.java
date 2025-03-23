package orders.service;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.exceptions.OrderNotFoundException;
import orders.mapper.OrderMapper;
import orders.repository.OrderRepository;
import orders.repository.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper orderMapper;

    public List<OrderDetailsResponse> findAllNew() {
        return repository.findAllNew().stream().map(orderMapper::map).toList();
    }

    public List<Integer> findCompletedIds() {
        return repository.findCompletedIds();
    }

    public List<Integer> findInvoicedIds() {
        return repository.findInvoicedIds();
    }

    public List<Integer> findNewIds() {
        return repository.findNewIds();
    }

    public List<Integer> findRejectedIds() {
        return repository.findRejectedIds();
    }

    public List<OrderDetailsResponse> findAllNewForClientId(Integer id) {
        return repository.findAllNewForClientId(id).stream().map(orderMapper::map).toList();
    }

    public List<OrderDetailsResponse> findAllNewForProductId(Integer id) {
        return repository.findAllNewForProductId(id).stream().map(orderMapper::map).toList();
    }

    public OrderDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(orderMapper::map)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public IdResponse create(CreateOrderRequest request) {
        Order order = orderMapper.map(request);
        repository.save(order);
        log.info("Created {}", order);
        return new IdResponse(order.getId());
    }

    public void complete(Integer id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (!order.isNew()) {
            return;
        }
        order.complete();
        log.info("Completed {}", id);
    }

    public void markAsSentToInvoice(Integer id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (!order.isCompleted()) {
            return;
        }
        order.markAsSentToInvoice();
        log.info("Sent to invoice {}", id);
    }

    public void invoice(Integer id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (!order.isSentToInvoice()) {
            return;
        }
        order.markAsInvoiced();
        log.info("Invoiced {}", id);
    }

    public void cancel(Integer id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (!order.isNew()) {
            return;
        }
        order.cancel();
        log.info("Cancelled {}", id);
    }

    public void cancelByProductId(Integer id) {
        repository.findActiveIdsByProductId(id).forEach(this::cancel);
    }

    public void reject(Integer id, String reason) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (!order.isNew()) {
            return;
        }
        order.reject(reason);
        log.info("Rejected {}", id);
    }

    public boolean existsByProductId(Integer id) {
        return repository.existsByProductId(id);
    }

    public boolean existsByClientId(Integer id) {
        return repository.existsByClientId(id);
    }

    public long countAllNew() {
        return repository.countAllNew();
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("Deleted {}", id);
    }

}
