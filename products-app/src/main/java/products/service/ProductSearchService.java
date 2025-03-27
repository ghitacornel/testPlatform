package products.service;

import contracts.products.ProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.exceptions.ProductNotFoundException;
import products.mapper.ProductMapper;
import products.repository.ProductRepository;
import products.repository.entity.Status;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductDetailsResponse> findAllActive() {
        return repository.findByStatus(Status.ACTIVE).stream().map(mapper::map).toList();
    }

    public List<ProductDetailsResponse> findAllActiveForCompany(Integer id) {
        return repository.findAllActiveForCompany(id).stream().map(mapper::map).toList();
    }

    public long countAllActive() {
        return repository.countByStatus(Status.ACTIVE);
    }

    public ProductDetailsResponse findById(Integer id) {
        return repository.findById(id).map(mapper::map).orElseThrow(() -> new ProductNotFoundException(id));
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

    public boolean existsByCompanyId(Integer id) {
        return repository.existsByCompanyId(id);
    }

}
