package products.service;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.clients.company.CompanyClient;
import products.clients.order.CreateOrderRequest;
import products.clients.order.CreateOrderResponse;
import products.clients.order.OrderClient;
import products.controller.model.request.ProductBuyRequest;
import products.controller.model.request.ProductSaleRequest;
import products.controller.model.response.ProductDetailsResponse;
import products.repository.ProductRepository;
import products.repository.entity.Product;
import products.repository.entity.ProductStatus;
import products.service.mapper.ProductMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CompanyClient companyClient;
    private final OrderClient orderClient;
    private final ProductMapper productMapper;

    public List<ProductDetailsResponse> findAll() {
        return repository.findAll().stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    public long countAll() {
        return repository.count();
    }

    public ProductDetailsResponse sale(ProductSaleRequest request) {
        if (companyClient.findById(request.getCompanyId()) == null) {
            throw new EntityNotFoundException("Company with id " + request.getCompanyId() + " not found");
        }
        Product product = productMapper.map(request);
        repository.save(product);
        log.info("new product to sale " + product);
        return productMapper.map(product);
    }

    public void cancel(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        product.setStatus(ProductStatus.CANCELLED);
        log.info("product cancelled " + product);
    }

    public ProductDetailsResponse buy(ProductBuyRequest request) {
        Product product = repository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + request.getProductId() + " not found"));
        if (product.getQuantity() < request.getQuantity()) {
            throw new BusinessException("Cannot buy more that it exists, want to buy " + request.getQuantity() + " from remaining " + product.getQuantity());
        }

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setProductId(request.getProductId());
        createOrderRequest.setClientId(request.getClientId());
        createOrderRequest.setProductQuantity(request.getQuantity());
        CreateOrderResponse order = orderClient.createOrder(createOrderRequest);
        log.info("new order " + order);

        product.setQuantity(product.getQuantity() - request.getQuantity());

        // no more products => delete
        if (product.getQuantity() == 0) {
            cancel(product.getId());
        }

        return productMapper.map(product);
    }

    public ProductDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(productMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

}
