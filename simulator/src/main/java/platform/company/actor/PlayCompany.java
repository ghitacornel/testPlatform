package platform.company.actor;

import commons.model.IdResponse;
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

    private static final int MINIMUM = 50;
    private static final int MAXIMUM = 100;
    private final CompanyService companyService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void register() {
        if (companyService.count() > MAXIMUM) {
            return;
        }
        Company company = randomDataCreatorService.createCompany();
        IdResponse response = companyService.register(company);
        log.debug("registered company " + response);
    }

    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    public void unregister() {
        if (companyService.count() < MINIMUM) {
            return;
        }
        Company company = randomDataFetchService.findRandomCompany();
        companyService.unregister(company);
        log.debug("unregistered company " + company);
    }

}
