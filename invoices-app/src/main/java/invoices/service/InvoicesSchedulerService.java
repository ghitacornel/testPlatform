package invoices.service;

import invoices.repository.InvoiceRepository;
import invoices.repository.entity.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoicesSchedulerService {

    private final InvoiceRepository repository;

    public void removeCompleted() {
        repository.deleteByStatusAndReferenceDateTime(Status.COMPLETED, Instant.now().minusSeconds(60));
    }

    public void removeError() {
        repository.deleteByStatusAndReferenceDateTime(Status.ERROR, Instant.now().minusSeconds(60));
    }

}
