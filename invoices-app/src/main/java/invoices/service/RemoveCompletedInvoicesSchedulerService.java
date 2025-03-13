package invoices.service;

import invoices.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveCompletedInvoicesSchedulerService {

    private final InvoiceRepository repository;
    private final RemoveCompletedInvoicesSchedulerServiceHelper helper;

    public void removeCompleted() {
        repository.findAllCompleted().forEach(helper::remove);
    }

}
