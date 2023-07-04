package products.controller;

import commons.model.IdResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import products.controller.model.request.ProductBuyRequest;
import products.controller.model.request.ProductSellRequest;
import products.controller.model.response.ProductDetailsResponse;
import products.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductDetailsResponse> findAllActive() {
        return service.findAllActive();
    }

    @GetMapping("count")
    public long countAllActive() {
        return service.countAllActive();
    }

    @GetMapping(value = "{id}")
    public ProductDetailsResponse findById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping(value = "sell")
    public IdResponse sell(@Valid @RequestBody ProductSellRequest request) {
        return service.sell(request);
    }

    @PutMapping(value = "buy")
    public void buy(@Valid @RequestBody ProductBuyRequest request) {
        service.buy(request);
    }

    @DeleteMapping(value = "{id}")
    public void cancel(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.cancel(id);
    }
}
