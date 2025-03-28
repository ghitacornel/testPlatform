package orders.service;

import commons.model.IdResponse;
import contracts.orders.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.exceptions.OrderNotFoundException;
import orders.mapper.OrderMapper;
import orders.repository.OrderRepository;
import orders.repository.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public IdResponse create(CreateOrderRequest request) {
        Order order = mapper.map(request);
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
        if (order.isInvoiced()) {
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
        if (order.isRejected()) {
            return;
        }
        if (!order.isNew()) {
            log.warn("Order in {} cannot be rejected {}", order.getStatus(), id);
            return;
        }
        order.reject(reason);
        log.info("Rejected {} {}", id, reason);
    }

}
