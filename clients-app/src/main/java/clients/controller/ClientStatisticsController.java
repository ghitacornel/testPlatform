package clients.controller;

import clients.controller.model.response.ClientStatistics;
import clients.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientStatisticsController {

    private final StatisticsService service;

    @GetMapping
    public ClientStatistics statistics() {
        return service.getStatistics();
    }

}
