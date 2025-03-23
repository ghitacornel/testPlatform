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

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;
    private final InvoiceMapper invoiceMapper;

    public List<InvoiceDetails> findAll() {
        return repository.findAll().stream().map(invoiceMapper::map).toList();
    }

    public long count() {
        return repository.count();
    }

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
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        invoiceMapper.update(invoice, request);
        log.info("Order updated: {}", invoice);
    }

    public void updateClient(UpdateClientRequest request) {
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        invoiceMapper.update(invoice, request);
        log.info("Client updated: {}", invoice);
    }

    public void updateCompany(UpdateCompanyRequest request) {
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        invoiceMapper.update(invoice, request);
        log.info("Company updated: {}", invoice);
    }

    public void updateProduct(UpdateProductRequest request) {
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new InvoiceNotFoundException(request.getId()));
        invoiceMapper.update(invoice, request);
        log.info("Product updated: {}", invoice);
    }

    public void complete(Integer id) {
        repository.complete(id);
        log.info("Completed: {}", id);
    }

    public void error(Integer id) {
        repository.error(id);
        log.error("Error: {}", id);
    }

    public boolean existsByOrderId(Integer id) {
        return repository.existsById(id);
    }

    public boolean existsByClientId(Integer id) {
        return repository.existsByClientId(id);
    }

    public boolean existsByCompanyId(Integer id) {
        return repository.existsByCompanyId(id);
    }

    public boolean existsByProductId(Integer id) {
        return repository.existsByProductId(id);
    }

}
