package orders.services;

import lombok.RequiredArgsConstructor;
import orders.clients.client.ClientClient;
import orders.clients.client.ClientDto;
import orders.clients.company.CompanyClient;
import orders.clients.company.CompanyDto;
import orders.clients.products.ProductClient;
import orders.clients.products.ProductDto;
import orders.controllers.models.CreateOrderRequest;
import orders.controllers.models.OrderDto;
import orders.repositories.entities.Order;
import orders.repositories.OrderRepository;
import orders.services.mappers.OrderMapper;
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

    public List<OrderDto> findAll() {
        return repository.findAll().stream()
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    public OrderDto findById(Integer id) {
        return repository.findById(id)
                .map(orderMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    public OrderDto create(CreateOrderRequest request) {
        ClientDto client = clientClient.findByName(request.getUserName());
        CompanyDto company = companyClient.findByName(request.getCompanyName());
        ProductDto product = productClient.findById(request.getProductId());
        Order order = new Order();
        order.setUserId(client.getId());
        order.setUserName(client.getName());
        order.setUserCreditCardType(client.getCardType());
        order.setCompanyId(company.getId());
        order.setCompanyName(company.getName());
        order.setProductName(product.getName());
        order.setProductColor(product.getColor());
        order.setProductPrice(product.getPrice());
        order.setProductQuantity(product.getQuantity());
        repository.save(order);
        return orderMapper.map(order);
    }
}
