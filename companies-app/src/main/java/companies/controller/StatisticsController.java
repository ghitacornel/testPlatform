package companies.controller;

import companies.repository.CompanyRepository;
import companies.repository.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatisticsController {

    private final CompanyRepository repository;

    @GetMapping
    public Map<String, Object> statistics() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("all", repository.count());
        result.put("active", repository.countByStatus(Status.ACTIVE));
        result.put("retired", repository.countByStatus(Status.RETIRED));
        return result;
    }

}
