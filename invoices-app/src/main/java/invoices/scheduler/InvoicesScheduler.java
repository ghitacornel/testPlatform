package invoices.scheduler;

import invoices.service.InvoicesSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InvoicesScheduler {

    private final InvoicesSchedulerService service;

    @Scheduled(fixedRate = 10000)
    void removeCompleted() {
        service.removeCompleted();
    }

    @Scheduled(fixedRate = 10000)
    void removeError() {
        service.removeError();
    }

}
