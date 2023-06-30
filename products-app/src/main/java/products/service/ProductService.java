package products.service;

import commons.exceptions.BusinessException;
import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.controller.model.request.ProductBuyRequest;
import products.controller.model.request.ProductSaleRequest;
import products.controller.model.response.ProductDetailsResponse;
import products.repository.ProductRepository;
import products.repository.entity.Product;
import products.repository.entity.ProductStatus;
import products.mapper.ProductMapper;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper productMapper;

    public List<ProductDetailsResponse> findAllActive() {
        return repository.findAllActive().stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    public long countAllActive() {
        return repository.countAllActive();
    }

    public IdResponse sale(ProductSaleRequest request) {
        Product product = productMapper.map(request);
        repository.save(product);
        log.debug("new product to sale " + product);
        return new IdResponse(product.getId());
    }

    public void cancel(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        product.setStatus(ProductStatus.CANCELLED);
        log.debug("product cancelled " + product);
    }

    public void buy(ProductBuyRequest request) {

        Product product = repository.findByIdAndLock(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + request.getProductId() + " not found"));

        if (product.getQuantity() < request.getQuantity()) {
            throw new BusinessException("Cannot buy more that it exists, want to buy " + request.getQuantity() + " from remaining " + product.getQuantity());
        }

        product.setQuantity(product.getQuantity() - request.getQuantity());

    }

    public ProductDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(productMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

}
