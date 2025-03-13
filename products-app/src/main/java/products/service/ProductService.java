package products.service;

import commons.model.IdResponse;
import contracts.products.ProductBuyRequest;
import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.exceptions.CannotBuyMoreThanAvailableException;
import products.mapper.ProductMapper;
import products.repository.ProductRepository;
import products.repository.entity.Product;

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
        repository.getReferenceById(id).cancel();
        log.info("canceled {}", id);
    }

    public void buy(ProductBuyRequest request) {

        Product product = repository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + request.getProductId() + " not found"));

        if (product.getQuantity() < request.getQuantity()) {
            throw new CannotBuyMoreThanAvailableException(request.getQuantity(), product.getQuantity());
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
        repository.getReferenceById(id).refill(quantity);
        log.info("refill {} with quantity {}", id, quantity);
    }

    public void cancelByCompany(Integer id) {
        repository.findByCompanyId(id).forEach(product -> {
            log.info("cancelled by company {}", product.getId());
            product.cancel();
        });
    }

    public List<Integer> findConsumedIds() {
        return repository.findConsumedIds();
    }

    public List<Integer> findCancelledIds() {
        return repository.findCancelledIds();
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("deleted {}", id);
    }

}
