package products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import products.repository.ProductRepository;
import products.repository.entity.ProductStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatisticsController {

    private final ProductRepository repository;

    @GetMapping
    public Map<String, Object> statistics() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("all", repository.count());
        result.put("active", repository.countByStatus(ProductStatus.ACTIVE));
        result.put("consumed", repository.countByStatus(ProductStatus.CONSUMED));
        result.put("cancelled", repository.countByStatus(ProductStatus.CANCELLED));
        return result;
    }

}
