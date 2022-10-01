package platform.company.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import platform.common.AbstractActor;
import platform.company.model.Company;
import platform.company.service.CompanyService;
import platform.random.RandomDataCreatorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetupCompany extends AbstractActor {

    private static final int MAX_COMPANY_NUMBERS = 50;
    private final CompanyService service;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {
        tearDown();
        Set<Company> items = new HashSet<>();
        while (items.size() < MAX_COMPANY_NUMBERS) items.add(randomDataCreatorService.createCompany());
        for (Company item : items) {
            Company registered = service.register(item);
            log.info("registered " + registered);
        }
    }

    @PreDestroy
    public void tearDown() {
        for (Company item : service.findAll()) {
            log.info("unregistering " + item);
            service.unregister(item);
        }
    }
}
