package orders.controller;

import lombok.RequiredArgsConstructor;
import orders.controller.model.response.OrderStatistics;
import orders.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderStatisticsController {

    private final OrderService service;

    @GetMapping
    public OrderStatistics statistics() {
        return service.getStatistics();
    }

}
