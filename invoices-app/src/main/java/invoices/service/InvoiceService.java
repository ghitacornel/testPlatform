package invoices.service;

import commons.model.IdResponse;
import invoices.controller.model.request.*;
import invoices.controller.model.response.InvoiceDetails;
import invoices.controller.model.response.InvoiceStatistics;
import invoices.mapper.InvoiceMapper;
import invoices.repository.InvoiceRepository;
import invoices.repository.entity.Invoice;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;
    private final InvoiceMapper invoiceMapper;

    public List<InvoiceDetails> findAll() {
        return repository.findAll().stream()
                .map(invoiceMapper::map)
                .collect(Collectors.toList());
    }

    public long count() {
        return repository.count();
    }

    public IdResponse createInvoice(InvoiceCreateRequest request) {
        Invoice invoice = new Invoice();
        invoice.setId(request.getOrderId());
        repository.save(invoice);
        return new IdResponse(invoice.getId());
    }

    public InvoiceStatistics getStatistics() {
        return InvoiceStatistics.builder()
                .countAll(repository.count())
                .build();
    }

    public void updateOrder(UpdateOrderRequest request) {
        Invoice invoice = repository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getOrderId() + " not found"));
        invoice.setOrderQuantity(request.getQuantity());
    }

    public void updateClient(UpdateClientRequest request) {
        Invoice invoice = repository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getOrderId() + " not found"));
        invoice.setClientId(request.getClientId());
        invoice.setClientName(request.getClientName());
        invoice.setClientCardType(request.getClientCardType());
        invoice.setClientCountry(request.getClientCountry());
    }

    public void updateCompany(UpdateCompanyRequest request) {
        Invoice invoice = repository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getOrderId() + " not found"));
        invoice.setCompanyId(request.getCompanyId());
        invoice.setCompanyName(request.getCompanyName());
        invoice.setCompanyUrl(request.getCompanyUrl());
        invoice.setCompanyIndustry(request.getCompanyIndustry());
        invoice.setCompanyCountry(request.getCompanyCountry());
    }

    public void updateProduct(UpdateProductRequest request) {
        Invoice invoice = repository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice with id " + request.getOrderId() + " not found"));
        invoice.setProductId(request.getProductId());
        invoice.setProductName(request.getProductName());
        invoice.setProductColor(request.getProductColor());
        invoice.setProductPrice(request.getProductPrice());
    }
}
