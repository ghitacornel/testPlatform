package invoices.scheduler;

import invoices.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveCompletedInvoicesScheduler {

    private final InvoiceRepository repository;

    @Scheduled(fixedRate = 10000)
    private void removeCompletedInvoices() {
        repository.findAllCompleted().forEach(invoice -> {
            log.info("Removing completed invoice {}", invoice);
            repository.delete(invoice);
        });
    }

}
