package products.service;

import commons.model.IdResponse;
import contracts.products.ProductBuyRequest;
import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.exceptions.CannotBuyMoreThanAvailableException;
import products.exceptions.ProductNotFoundException;
import products.mapper.ProductMapper;
import products.repository.ProductRepository;
import products.repository.entity.Product;
import products.repository.entity.Status;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper productMapper;

    public List<ProductDetailsResponse> findAllActive() {
        return repository.findByStatus(Status.ACTIVE).stream().map(productMapper::map).toList();
    }

    public List<ProductDetailsResponse> findAllActiveForCompany(Integer id) {
        return repository.findAllActiveForCompany(id).stream().map(productMapper::map).toList();
    }

    public long countAllActive() {
        return repository.countByStatus(Status.ACTIVE);
    }

    public IdResponse sell(ProductSellRequest request) {
        Product entity = productMapper.map(request);
        repository.save(entity);
        log.info("sell {}", entity);
        return new IdResponse(entity.getId());
    }

    public void cancel(Integer id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null) {
            log.warn("Product not found for cancellation {}", id);
            return;
        }
        product.cancel();
        log.info("canceled {}", id);
    }

    public void buy(ProductBuyRequest request) {

        Product product = repository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        if (product.getQuantity() < request.getQuantity()) {
            throw new CannotBuyMoreThanAvailableException(request.getProductId(), request.getQuantity(), product.getQuantity());
        }

        product.setQuantity(product.getQuantity() - request.getQuantity());
        if (product.getQuantity() == 0) {
            product.setStatus(Status.ACTIVE);
            log.info("consumed {}", request.getProductId());
        }

    }

    public ProductDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(productMapper::map)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void refill(Integer id, Integer quantity) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.refill(quantity);
        log.info("refill {} with quantity {}", id, quantity);
    }

    public void cancelByCompany(Integer id) {
        repository.findByCompanyId(id).forEach(product -> {
            product.cancel();
            log.info("cancelled by company {}", product.getId());
        });
    }

    public List<Integer> findConsumedIds() {
        return repository.findIdsByStatus(Status.CONSUMED);
    }

    public List<Integer> findCancelledIds() {
        return repository.findIdsByStatus(Status.CANCELLED);
    }

    public List<Integer> findActiveIds() {
        return repository.findIdsByStatus(Status.ACTIVE);
    }

    public List<Integer> findAllActiveIdsForCompany(Integer id) {
        return repository.findAllActiveIdsForCompany(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("deleted {}", id);
    }

    public boolean existsByCompanyId(Integer id) {
        return repository.existsByCompanyId(id);
    }

}
