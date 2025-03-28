package flows.scheduler;

import flows.clients.CompanyClient;
import flows.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CompaniesScheduler {

    private final CompanyClient companyClient;
    private final CompanyService companyService;

    @Scheduled(fixedRate = 10000)
    void deleteRetiring() {
        companyClient.findRetiringIds().forEach(companyService::deleteCompany);
    }

}

