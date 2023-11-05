package invoices.controller;

import invoices.controller.model.response.InvoiceStatistics;
import invoices.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InvoiceStatisticsController {

    private final InvoiceService service;

    @GetMapping
    public InvoiceStatistics statistics() {
        return service.getStatistics();
    }

}
