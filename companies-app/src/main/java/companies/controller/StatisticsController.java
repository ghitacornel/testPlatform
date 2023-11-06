package companies.controller;

import companies.controller.model.response.Statistics;
import companies.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping
    public Statistics statistics() {
        return service.getStatistics();
    }

}
