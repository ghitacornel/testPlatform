package orders.service;

import commons.exceptions.ResourceNotFound;
import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import contracts.orders.OrderDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .orElseThrow(() -> new ResourceNotFound("Order with id " + id + " not found"));
    }

    public IdResponse create(CreateOrderRequest request) {
        Order order = orderMapper.map(request);
        repository.save(order);
        log.info("Created {}", order);
        return new IdResponse(order.getId());
    }

    public void complete(Integer id) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Not found for completion {}", id);
            return;
        }
        if (order.isCancelled()) {
            return;
        }
        order.complete();
        log.info("Completed {}", id);
    }

    public void markAsSentToInvoice(Integer id) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Not found for sending to invoice {}", id);
            return;
        }
        if (order.isCancelled()) {
            return;
        }
        order.markAsSentToInvoice();
        log.info("Sent to invoice {}", id);
    }

    public void invoice(Integer id) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Not found for invoice {}", id);
            return;
        }
        if (order.isCancelled()) {
            return;
        }
        order.markAsInvoiced();
        log.info("Invoiced {}", id);
    }

    public void cancel(Integer id) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Not found for cancel {}", id);
            return;
        }
        if (order.isCompleted()) {
            return;
        }
        if (order.isCancelled()) {
            return;
        }
        order.cancel();
        log.info("Cancelled {}", id);
    }

    public void cancelByProductId(Integer id) {
        repository.findActiveIdsByProductId(id).forEach(this::cancel);
    }

    public void reject(Integer id) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Not found for reject {}", id);
            return;
        }
        if (!order.isNew()) {
            return;
        }
        order.reject();
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
