package company.actor;

import commons.model.IdResponse;
import company.model.Company;
import company.random.RandomDataCreatorService;
import company.random.RandomDataFetchService;
import company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayCompany {

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 100;

    private final CompanyService companyService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void register() {
        if (companyService.count() > MAXIMUM) {
            return;
        }
        Company company = randomDataCreatorService.createCompany();
        IdResponse response = companyService.register(company);
        log.info("registered company " + response);
    }

    @Scheduled(fixedRate = 30000, initialDelay = 45000)
    public void unregister() {
        if (companyService.count() < MINIMUM) {
            return;
        }
        Company company = randomDataFetchService.findRandomCompany();
        companyService.unregister(company);
        log.info("unregistered company " + company);
    }

}
