package invoices.controller;

import invoices.repository.InvoiceRepository;
import invoices.repository.entity.InvoiceStatus;
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

    private final InvoiceRepository repository;

    @GetMapping
    public Map<String, Object> statistics() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("all", repository.count());
        result.put("new", repository.countByStatus(InvoiceStatus.NEW));
        result.put("completed", repository.countByStatus(InvoiceStatus.COMPLETED));
        result.put("error", repository.countByStatus(InvoiceStatus.ERROR));
        return result;
    }

}
