package products.controller;

import commons.model.IdResponse;
import contracts.products.ProductBuyRequest;
import contracts.products.ProductContract;
import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import products.service.ProductService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController implements ProductContract {

    private final ProductService service;

    public List<ProductDetailsResponse> findAllActive() {
        return service.findAllActive();
    }

    @Override
    public List<ProductDetailsResponse> findAllActiveForCompany(Integer id) {
        return service.findAllActiveForCompany(id);
    }

    public long countAllActive() {
        return service.countAllActive();
    }

    public ProductDetailsResponse findById(Integer id) {
        return service.findById(id);
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

}
