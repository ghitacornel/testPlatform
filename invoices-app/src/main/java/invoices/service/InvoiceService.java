package invoices.service;

import commons.model.IdResponse;
import contracts.invoices.*;
import invoices.exceptions.InvoiceNotFoundException;
import invoices.mapper.InvoiceMapper;
import invoices.repository.InvoiceRepository;
import invoices.repository.entity.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;

    public IdResponse createInvoice(InvoiceCreateRequest request) {
        if (repository.existsById(request.getOrderId())) {
            return new IdResponse(request.getOrderId());
        }
        Invoice invoice = new Invoice();
        invoice.setId(request.getOrderId());
        repository.save(invoice);
        log.info("Created: {}", invoice);
        return new IdResponse(invoice.getId());
    }

    public void updateOrder(UpdateOrderRequest request) {
        Invoice invoice = repository.findById(request.getId()).orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        mapper.update(invoice, request);
        log.info("Order updated: {}", invoice);
    }

    public void updateClient(UpdateClientRequest request) {
        Invoice invoice = repository.findById(request.getId()).orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        mapper.update(invoice, request);
        log.info("Client updated: {}", invoice);
    }

    public void updateCompany(UpdateCompanyRequest request) {
        Invoice invoice = repository.findById(request.getId()).orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        mapper.update(invoice, request);
        log.info("Company updated: {}", invoice);
    }

    public void updateProduct(UpdateProductRequest request) {
        Invoice invoice = repository.findById(request.getId()).orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        mapper.update(invoice, request);
        log.info("Product updated: {}", invoice);
    }

    public void complete(Integer id) {
        Invoice invoice = repository.findById(id).orElse(null);
        if (invoice == null) {
            log.warn("Invoice not found for completion {}", id);
            return;
        }
        invoice.complete();
        log.info("Completed: {}", id);
    }

    public void error(Integer id, String message) {
        Invoice invoice = repository.findById(id).orElse(null);
        if (invoice == null) {
            log.warn("Invoice not found for error {}", id);
            return;
        }
        invoice.error(message);
        log.error("{} {} ", id, message);
    }

}
