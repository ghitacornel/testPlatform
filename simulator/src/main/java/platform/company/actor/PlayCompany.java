package platform.company.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.common.AbstractActor;
import platform.company.model.Company;
import platform.company.service.CompanyService;
import platform.random.RandomDataCreatorService;
import platform.random.RandomDataFetchService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayCompany extends AbstractActor {

    private final CompanyService companyService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void register() {
        Company company = randomDataCreatorService.createCompany();
        company = companyService.register(company);
        log.info("registered company " + company);
    }

    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void unregister() {
        Company company = randomDataFetchService.findRandomCompany();
        companyService.unregister(company);
        log.info("unregistered company " + company);
    }

}
