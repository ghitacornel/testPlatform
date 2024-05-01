package invoices.controller;

import commons.model.IdResponse;
import invoices.controller.model.request.*;
import invoices.controller.model.response.InvoiceDetails;
import invoices.service.InvoiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PatchMapping("update/order")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrder(@Valid @RequestBody UpdateOrderRequest request) {
        service.updateOrder(request);
    }

    @PatchMapping("update/client")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClient(@Valid @RequestBody UpdateClientRequest request) {
        service.updateClient(request);
    }

    @PatchMapping("update/company")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompany(@Valid @RequestBody UpdateCompanyRequest request) {
        service.updateCompany(request);
    }

    @PatchMapping("update/product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@Valid @RequestBody UpdateProductRequest request) {
        service.updateProduct(request);
    }

    @PatchMapping("complete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void complete(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.complete(id);
    }
}
