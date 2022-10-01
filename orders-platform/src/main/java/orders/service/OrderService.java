package orders.service;

import lombok.RequiredArgsConstructor;
import orders.clients.client.ClientClient;
import orders.clients.client.ClientDto;
import orders.clients.company.CompanyClient;
import orders.clients.company.CompanyDto;
import orders.clients.products.ProductClient;
import orders.clients.products.ProductDto;
import orders.controller.model.request.CreateOrderRequest;
import orders.controller.model.response.OrderDetailsResponse;
import orders.repository.entity.Order;
import orders.repository.OrderRepository;
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

    public List<OrderDetailsResponse> findAll() {
        return repository.findAll().stream()
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
