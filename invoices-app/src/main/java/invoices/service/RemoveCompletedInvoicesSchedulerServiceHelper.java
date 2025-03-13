package invoices.service;

import invoices.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveCompletedInvoicesSchedulerServiceHelper {

    private final InvoiceRepository repository;

    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void remove(Integer id) {
        repository.deleteById(id);
        log.info("Deleted {}", id);
    }

}
