package flows.scheduler;

import flows.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CompaniesScheduler {

    private final CompanyService companyService;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    void deleteRetired() {
        companyService.deleteRetired();
    }

}

