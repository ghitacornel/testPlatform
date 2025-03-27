package invoices.service;

import contracts.invoices.*;
import invoices.mapper.InvoiceMapper;
import invoices.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceSearchService {

    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;

    public List<InvoiceDetails> findAll() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    public long count() {
        return repository.count();
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
