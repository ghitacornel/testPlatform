package orders.controller;

import contracts.orders.OrderStatus;
import lombok.RequiredArgsConstructor;
import orders.repository.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatisticsController {

    private final OrderRepository repository;

    @GetMapping
    public Map<String, Object> statistics() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("all", repository.count());
        result.put("new", repository.countByStatus(OrderStatus.NEW));
        result.put("completed", repository.countByStatus(OrderStatus.COMPLETED));
        result.put("sentToInvoice", repository.countByStatus(OrderStatus.SENT_TO_INVOICE));
        result.put("invoiced", repository.countByStatus(OrderStatus.INVOICED));
        result.put("cancelled", repository.countByStatus(OrderStatus.CANCELLED));
        result.put("rejected", repository.countByStatus(OrderStatus.REJECTED));
        return result;
    }

}
