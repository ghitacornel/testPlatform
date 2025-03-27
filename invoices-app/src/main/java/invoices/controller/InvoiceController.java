package invoices.controller;

import commons.model.IdResponse;
import contracts.invoices.*;
import invoices.service.InvoiceSearchService;
import invoices.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InvoiceController implements InvoiceContract {

    private final InvoiceService service;
    private final InvoiceSearchService searchService;

    public List<InvoiceDetails> findAll() {
        return searchService.findAll();
    }

    public long count() {
        return searchService.count();
    }

    public IdResponse create(InvoiceCreateRequest request) {
        return service.createInvoice(request);
    }

    public void update(UpdateOrderRequest request) {
        service.updateOrder(request);
    }

    public void update(UpdateClientRequest request) {
        service.updateClient(request);
    }

    public void update(UpdateCompanyRequest request) {
        service.updateCompany(request);
    }

    public void update(UpdateProductRequest request) {
        service.updateProduct(request);
    }

    public void complete(Integer id) {
        service.complete(id);
    }

    public void error(Integer id, String message) {
        service.error(id, message);
    }

    public boolean existsByOrderId(Integer id) {
        return searchService.existsByOrderId(id);
    }

    public boolean existsByClientId(Integer id) {
        return searchService.existsByClientId(id);
    }

    public boolean existsByCompanyId(Integer id) {
        return searchService.existsByCompanyId(id);
    }

    public boolean existsByProductId(Integer id) {
        return searchService.existsByProductId(id);
    }

}
