package products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import products.controller.model.response.Statistics;
import products.service.StatisticsService;

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
