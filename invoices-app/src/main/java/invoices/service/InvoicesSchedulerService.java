package invoices.service;

import invoices.repository.InvoiceRepository;
import invoices.repository.entity.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InvoicesSchedulerService {

    private final InvoiceRepository repository;

    public void removeCompleted() {
        repository.deleteByStatusAndReferenceDateTime(InvoiceStatus.COMPLETED, Instant.now().minusSeconds(60));
    }

    public void removeError() {
        repository.deleteByStatusAndReferenceDateTime(InvoiceStatus.ERROR, Instant.now().minusSeconds(60));
    }

}
