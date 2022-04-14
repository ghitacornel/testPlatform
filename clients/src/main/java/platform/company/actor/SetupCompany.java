package platform.company.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import platform.common.AbstractActor;
import platform.company.model.Company;
import platform.company.service.CompanyService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetupCompany extends AbstractActor {

    private final CompanyService service;

    @PostConstruct
    public void setUp() {
        tearDown();
        Set<Company> items = new HashSet<>();
        while (items.size() < 50) items.add(createNew());
        for (Company item : items) {
            Company registered = service.register(item);
            log.info("registered " + registered);
        }
    }

    private Company createNew() {
        Company item = new Company();
        item.setName(faker.company().name());
        item.setIndustry(faker.company().industry());
        item.setUrl(faker.company().url());
        return item;
    }

    @PreDestroy
    public void tearDown() {
        for (Company item : service.findAll()) {
            log.info("unregistering " + item);
            service.unregister(item);
        }
    }
}
