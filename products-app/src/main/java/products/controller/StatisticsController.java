package products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import products.repository.ProductRepository;
import products.repository.entity.Status;

import java.util.Arrays;
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
        Arrays.stream(Status.values()).forEach(status -> result.put(status.name().toLowerCase(), repository.countByStatus(status)));
        return result;
    }

}
