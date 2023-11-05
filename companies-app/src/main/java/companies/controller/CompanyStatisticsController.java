package companies.controller;

import companies.controller.model.response.CompanyStatistics;
import companies.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompanyStatisticsController {

    private final CompanyService service;

    @GetMapping
    public CompanyStatistics statistics() {
        return service.getStatistics();
    }

}
