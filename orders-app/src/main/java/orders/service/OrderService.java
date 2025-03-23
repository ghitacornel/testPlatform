package orders.service;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import contracts.orders.Status;
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
        return repository.findByStatus(Status.NEW).stream().map(orderMapper::map).toList();
    }

    public List<Integer> findCompletedIds() {
        return repository.findIdsByStatus(Status.COMPLETED);
    }

    public List<Integer> findInvoicedIds() {
        return repository.findIdsByStatus(Status.INVOICED);
    }

    public List<Integer> findNewIds() {
        return repository.findIdsByStatus(Status.NEW);
    }

    public List<Integer> findRejectedIds() {
        return repository.findIdsByStatus(Status.REJECTED);
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
        if (order.isCompleted()) {
            return;
        }
        if (!order.isNew()) {
            log.warn("Order in {} cannot be completed {}", order.getStatus(), id);
            return;
        }
        order.complete();
        log.info("Completed {}", id);
    }

    public void markAsSentToInvoice(Integer id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (order.isSentToInvoice()) {
            return;
        }
        if (!order.isCompleted()) {
            log.warn("Order in {} cannot be sent to invoice {}", order.getStatus(), id);
            return;
        }
        order.markAsSentToInvoice();
        log.info("Sent to invoice {}", id);
    }

    public void invoice(Integer id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (order.isInvoiced()){
            return;
        }
        if (!order.isSentToInvoice()) {
            log.warn("Order in {} cannot be invoiced {}", order.getStatus(), id);
            return;
        }
        order.markAsInvoiced();
        log.info("Invoiced {}", id);
    }

    public void cancel(Integer id) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Order not found for cancellation {}", id);
            return;
        }
        if (order.isCancelled()) {
            return;
        }
        if (!order.isNew()) {
            log.warn("Order in {} cannot be cancelled {}", order.getStatus(), id);
            return;
        }
        order.cancel();
        log.info("Cancelled {}", id);
    }

    public void cancelByProductId(Integer id) {
        repository.findNewIdsByProductId(id).forEach(this::cancel);
    }

    public void reject(Integer id, String reason) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Order not found for rejection {}", id);
            return;
        }
        if(order.isRejected()) {
            return;
        }
        if (!order.isNew()) {
            log.warn("Order in {} cannot be rejected {}", order.getStatus(), id);
            return;
        }
        order.reject(reason);
        log.info("Rejected {} {}", id, reason);
    }

    public boolean existsByProductId(Integer id) {
        return repository.existsByProductId(id);
    }

    public boolean existsByClientId(Integer id) {
        return repository.existsByClientId(id);
    }

    public long countAllNew() {
        return repository.countByStatus(Status.NEW);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("Deleted {}", id);
    }

}
