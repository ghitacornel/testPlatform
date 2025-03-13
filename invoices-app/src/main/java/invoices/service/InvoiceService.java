package invoices.service;

import commons.model.IdResponse;
import contracts.invoices.*;
import invoices.mapper.InvoiceMapper;
import invoices.repository.InvoiceRepository;
import invoices.repository.entity.Invoice;
import jakarta.persistence.EntityNotFoundException;
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
        return repository.findAll().stream()
                .map(invoiceMapper::map)
                .toList();
    }

    public long count() {
        return repository.count();
    }

    public IdResponse createInvoice(InvoiceCreateRequest request) {
        Invoice invoice = new Invoice();
        invoice.setId(request.getOrderId());
        repository.save(invoice);
        log.info("Invoice created: {}", invoice);
        return new IdResponse(invoice.getId());
    }

    public void updateOrder(UpdateOrderRequest request) {
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getId() + " not found"));
        invoiceMapper.update(invoice, request);
        log.info("Invoice order updated: {}", invoice);
    }

    public void updateClient(UpdateClientRequest request) {
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getId() + " not found"));
        invoiceMapper.update(invoice, request);
        log.info("Invoice client updated: {}", invoice);
    }

    public void updateCompany(UpdateCompanyRequest request) {
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getId() + " not found"));
        invoiceMapper.update(invoice, request);
        log.info("Invoice company updated: {}", invoice);
    }

    public void updateProduct(UpdateProductRequest request) {
        Invoice invoice = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getId() + " not found"));
        invoiceMapper.update(invoice, request);
        log.info("Invoice product updated: {}", invoice);
    }

    public void complete(Integer id) {
        repository.complete(id);
        log.info("Invoice completed: {}", id);
    }

}
