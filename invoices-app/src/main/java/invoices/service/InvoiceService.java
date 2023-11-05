package invoices.service;

import commons.model.IdResponse;
import invoices.controller.model.request.InvoiceCreateRequest;
import invoices.controller.model.response.InvoiceDetails;
import invoices.controller.model.response.InvoiceStatistics;
import invoices.mapper.InvoiceMapper;
import invoices.repository.InvoiceRepository;
import invoices.repository.entity.Invoice;
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
        invoice.setOrderId(request.getOrderId());
        repository.save(invoice);
        return new IdResponse(invoice.getId());
    }

    public InvoiceStatistics getStatistics() {
        return InvoiceStatistics.builder()
                .countAll(repository.count())
                .build();
    }
}
