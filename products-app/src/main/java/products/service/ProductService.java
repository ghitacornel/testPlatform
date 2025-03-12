package products.service;

import commons.exceptions.BusinessException;
import commons.model.IdResponse;
import contracts.products.ProductBuyRequest;
import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.mapper.ProductMapper;
import products.repository.ProductRepository;
import products.repository.entity.Product;
import products.repository.entity.ProductStatus;

import java.util.List;

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
                .toList();
    }

    public List<ProductDetailsResponse> findAllActiveForCompany(Integer id) {
        return repository.findAllActiveForCompany(id).stream()
                .map(productMapper::map)
                .toList();
    }

    public long countAllActive() {
        return repository.countAllActive();
    }

    public IdResponse sell(ProductSellRequest request) {
        Product entity = productMapper.map(request);
        repository.save(entity);
        log.info("sell {}", entity);
        return new IdResponse(entity.getId());
    }

    public void cancel(Integer id) {
        Product entity = repository.getReferenceById(id);
        entity.setStatus(ProductStatus.CANCELLED);
        log.info("cancel {}", id);
    }

    public void buy(ProductBuyRequest request) {

        Product product = repository.findByIdAndLock(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + request.getProductId() + " not found"));

        if (product.getQuantity() < request.getQuantity()) {
            throw new BusinessException("Cannot buy more that it exists, requested " + request.getQuantity() + " available " + product.getQuantity());
        }

        product.setQuantity(product.getQuantity() - request.getQuantity());
        if (product.getQuantity() == 0) {
            log.info("consumed {}", request.getProductId());
        }

    }

    public ProductDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(productMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

    public void refill(Integer id, Integer quantity) {
        Product entity = repository.getReferenceById(id);
        entity.setQuantity(entity.getQuantity() + quantity);
        log.info("refill {} with quantity {}", id, quantity);
    }

    public void cancelByCompany(Integer id) {
        repository.cancelByCompany(id);
        log.info("cancel by company {}", id);
    }
}
