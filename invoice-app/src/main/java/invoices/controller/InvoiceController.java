package invoices.controller;

import commons.model.IdResponse;
import invoices.controller.model.request.InvoiceCreateRequest;
import invoices.controller.model.response.InvoiceDetails;
import invoices.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("invoice")
@RequiredArgsConstructor
@Validated
public class InvoiceController {

    private final InvoiceService service;

    @GetMapping
    public List<InvoiceDetails> findAll() {
        return service.findAll();
    }

    @GetMapping("count")
    public long count() {
        return service.count();
    }

    @PostMapping
    public IdResponse createInvoice(@Valid @RequestBody InvoiceCreateRequest request) {
        return service.createInvoice(request);
    }

}
