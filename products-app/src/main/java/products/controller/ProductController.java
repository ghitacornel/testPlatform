package products.controller;

import commons.model.IdResponse;
import contracts.products.ProductBuyRequest;
import contracts.products.ProductContract;
import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import products.service.ProductSearchService;
import products.service.ProductService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController implements ProductContract {

    private final ProductService service;
    private final ProductSearchService searchService;

    public List<ProductDetailsResponse> findAllActive() {
        return searchService.findAllActive();
    }

    @Override
    public List<ProductDetailsResponse> findAllActiveForCompany(Integer id) {
        return searchService.findAllActiveForCompany(id);
    }

    public long countAllActive() {
        return searchService.countAllActive();
    }

    public ProductDetailsResponse findById(Integer id) {
        return searchService.findById(id);
    }

    public IdResponse sell(ProductSellRequest request) {
        return service.sell(request);
    }

    public void buy(ProductBuyRequest request) {
        service.buy(request);
    }

    public void cancel(Integer id) {
        service.cancel(id);
    }

    public void cancelByCompany(Integer id) {
        service.cancelByCompany(id);
    }

    public void refill(Integer id, Integer quantity) {
        service.refill(id, quantity);
    }

    public List<Integer> findConsumedIds() {
        return searchService.findConsumedIds();
    }

    public List<Integer> findCancelledIds() {
        return searchService.findCancelledIds();
    }

    public List<Integer> findActiveIds() {
        return searchService.findActiveIds();
    }

    public List<Integer> findAllActiveIdsForCompany(Integer id) {
        return searchService.findAllActiveIdsForCompany(id);
    }

    public void delete(Integer id) {
        service.delete(id);
    }

    public boolean existsByCompanyId(Integer id) {
        return searchService.existsByCompanyId(id);
    }

}
