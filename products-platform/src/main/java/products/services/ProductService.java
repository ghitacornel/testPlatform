package products.services;

import commons.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.clients.company.CompanyClient;
import products.clients.company.CompanyDto;
import products.clients.order.CreateOrderRequest;
import products.clients.order.OrderClient;
import products.services.mappers.ProductMapper;
import products.controllers.models.ProductBuyRequest;
import products.controllers.models.ProductDto;
import products.controllers.models.ProductSaleRequest;
import products.repositories.ProductRepository;
import products.repositories.entities.Product;


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

    public List<ProductDto> findAll() {
        return repository.findAll().stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    public long countAll() {
        return repository.count();
    }

    public ProductDto sale(ProductSaleRequest saleRequest) {
        CompanyDto company = companyClient.findByName(saleRequest.getCompanyName());
        Product product = new Product();
        product.setName(saleRequest.getName());
        product.setColor(saleRequest.getColor());
        product.setPrice(saleRequest.getPrice());
        product.setQuantity(saleRequest.getQuantity());
        product.setCompanyId(company.getId());
        product.setCompanyName(company.getName());
        repository.save(product);
        log.info("new product to sale " + product);
        return productMapper.map(product);
    }

    public void cancelSale(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        repository.delete(product);
        log.info("product cancelled " + product);
    }

    private Product lock(Integer id) {
        return repository.lockForProcess(id)
                .orElseThrow(() -> new EntityNotFoundException("product with id " + id + " not found"));
    }

    public ProductDto buy(ProductBuyRequest buyRequest) {
        Product product = lock(buyRequest.getProductId());
        if (product.getQuantity() < buyRequest.getQuantity())
            throw new BusinessException("Cannot buy more that it exists, want to buy " + buyRequest.getQuantity() + " from remaining " + product.getQuantity());

        CreateOrderRequest request = new CreateOrderRequest();
        request.setProductId(product.getId());
        request.setProductPrice(product.getPrice());
        request.setProductQuantity(buyRequest.getQuantity());
        request.setUserName(buyRequest.getUserName());
        request.setCompanyName(product.getCompanyName());
        orderClient.createOrder(request);

        product.setQuantity(product.getQuantity() - buyRequest.getQuantity());

        // no more products => delete
        if (product.getQuantity() == 0) {
            log.info("no more items for sale for " + product);
            repository.delete(product);
        }

        return productMapper.map(product);
    }

    public ProductDto findById(Integer id) {
        return repository.findById(id)
                .map(productMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

}
