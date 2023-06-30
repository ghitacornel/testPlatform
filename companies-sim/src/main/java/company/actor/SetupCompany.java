package company.actor;

import commons.model.IdResponse;
import company.common.AbstractActor;
import company.model.Company;
import company.random.RandomDataCreatorService;
import company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetupCompany extends AbstractActor {

    private static final int MAX_COMPANY_NUMBERS = 50;

    private final CompanyService companyService;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {
        while (companyService.count() < MAX_COMPANY_NUMBERS) {
            try {
                Company company = randomDataCreatorService.createCompany();
                IdResponse response = companyService.register(company);
                log.info("registered company " + response);
            } catch (Exception e) {
                log.error("fail creating a company, trying again", e);
            }
        }
    }

}
