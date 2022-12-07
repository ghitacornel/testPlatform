package orders.service;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import orders.clients.client.ClientClient;
import orders.clients.client.ClientDto;
import orders.clients.company.CompanyClient;
import orders.clients.company.CompanyDto;
import orders.clients.products.ProductClient;
import orders.clients.products.ProductDto;
import orders.controller.model.request.CreateOrderRequest;
import orders.controller.model.response.OrderDetailsResponse;
import orders.repository.OrderRepository;
import orders.repository.entity.Order;
import orders.repository.entity.OrderStatus;
import orders.service.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ClientClient clientClient;
    private final CompanyClient companyClient;
    private final ProductClient productClient;
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

    public OrderDetailsResponse create(CreateOrderRequest request) {
        ClientDto client = clientClient.findById(request.getClientId());
        ProductDto product = productClient.findById(request.getProductId());
        CompanyDto company = companyClient.findById(product.getCompanyId());
        Order order = new Order();
        order.setUserId(client.getId());
        order.setUserName(client.getName());
        order.setUserCreditCardType(client.getCardType());
        order.setUserCountry(client.getCountry());
        order.setCompanyId(company.getId());
        order.setCompanyName(company.getName());
        order.setCompanyCountry(company.getCountry());
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setProductColor(product.getColor());
        order.setPrice(product.getPrice());
        order.setQuantity(product.getQuantity());
        repository.save(order);
        return orderMapper.map(order);
    }

    public void complete(Integer id) {
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
