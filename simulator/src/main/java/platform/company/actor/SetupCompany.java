package platform.company.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import platform.common.AbstractActor;
import platform.company.model.Company;
import platform.company.service.CompanyService;
import platform.random.RandomDataCreatorService;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetupCompany extends AbstractActor {

    private static final int MAX_COMPANY_NUMBERS = 50;
    private final CompanyService companyService;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {
        while (companyService.findAll().size() < MAX_COMPANY_NUMBERS) {
            try {
                Company company = randomDataCreatorService.createCompany();
                company = companyService.register(company);
                log.info("registered company " + company);
            } catch (Exception e) {
                log.error("fail creating a company, trying again", e);
            }
        }
    }

}
