package invoices.scheduler;

import invoices.repository.InvoiceRepository;
import invoices.repository.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
class InvoicesScheduler {

    private final InvoiceRepository repository;

    @Scheduled(fixedRate = 10000)
    void removeCompleted() {
        repository.deleteByStatusAndReferenceDateTime(Status.COMPLETED, Instant.now().minusSeconds(60));
    }

    @Scheduled(fixedRate = 10000)
    void removeError() {
        repository.deleteByStatusAndReferenceDateTime(Status.ERROR, Instant.now().minusSeconds(60));
    }

}
