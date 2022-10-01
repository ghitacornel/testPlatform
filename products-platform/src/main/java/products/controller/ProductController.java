package products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import products.controller.model.ProductBuyRequest;
import products.controller.model.ProductDto;
import products.controller.model.ProductSaleRequest;
import products.service.ProductService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<ProductDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/count")
    public long countAll() {
        return service.countAll();
    }

    @GetMapping(value = "{id}")
    public ProductDto findById(@Validated @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping(value = "/sale")
    public ProductDto sale(@Validated @RequestBody ProductSaleRequest json) {
        return service.sale(json);
    }

    @PostMapping(value = "/buy")
    public ProductDto buy(@Validated @RequestBody ProductBuyRequest json) {
        return service.buy(json);
    }

    @DeleteMapping(value = "{id}")
    public void cancelSale(@Validated @NotNull @PathVariable(name = "id") Integer id) {
        service.cancelSale(id);
    }
}
