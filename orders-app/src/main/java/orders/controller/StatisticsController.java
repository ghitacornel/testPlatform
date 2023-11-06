package orders.controller;

import lombok.RequiredArgsConstructor;
import orders.controller.model.response.Statistics;
import orders.service.StatisticsService;
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
