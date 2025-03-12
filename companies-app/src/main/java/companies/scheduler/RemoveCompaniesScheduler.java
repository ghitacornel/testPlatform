package companies.scheduler;

import companies.service.RemoveCompaniesSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveCompaniesScheduler {

    private final RemoveCompaniesSchedulerService service;

    @Scheduled(fixedRate = 10000)
    private void removeRetiredCompanies() {
        service.removeRetiredCompanies();
    }

}
