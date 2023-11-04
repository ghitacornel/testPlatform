package products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import products.controller.model.response.ProductStatistics;
import products.service.ProductService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductsStatisticsController {

    private final ProductService service;

    @GetMapping
    public ProductStatistics statistics() {
        return service.getStatistics();
    }

}
