package invoices.scheduler;

import invoices.service.RemoveCompletedInvoicesSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveCompletedInvoicesScheduler {

    private final RemoveCompletedInvoicesSchedulerService service;

    @Scheduled(fixedRate = 10000)
    private void removeCompleted() {
        service.removeCompleted();
    }

    @Scheduled(fixedRate = 10000)
    private void removeError() {
        service.removeError();
    }

}
