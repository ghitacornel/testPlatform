package orders.service;

import commons.exceptions.BusinessException;
import commons.model.IdResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import orders.controller.model.request.CreateOrderRequest;
import orders.controller.model.response.OrderDetailsResponse;
import orders.repository.OrderRepository;
import orders.repository.entity.Order;
import orders.repository.entity.OrderStatus;
import orders.service.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper orderMapper;

    public List<OrderDetailsResponse> findAllNew() {
        return repository.findAllNew().stream()
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    public OrderDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(orderMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    public IdResponse create(CreateOrderRequest request) {
        Order order = orderMapper.map(request);
        repository.save(order);
        return new IdResponse(order.getId());
    }

    public void completeById(Integer id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
        if (!order.getStatus().equals(OrderStatus.NEW)) {
            throw new BusinessException("cannot complete already completed order");
        }
        order.setStatus(OrderStatus.COMPLETED);
    }

    public void deleteById(Integer id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
        if (!order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new BusinessException("cannot delete an incomplete order");
        }
        repository.delete(order);
    }

    public boolean existsByProductId(Integer id) {
        return repository.existsByProductId(id);
    }

    public long countAllNew() {
        return repository.countAllNew();
    }
}
