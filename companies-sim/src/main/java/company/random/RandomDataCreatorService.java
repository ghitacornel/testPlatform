package company.random;

import company.common.AbstractActor;
import company.model.Company;
import org.springframework.stereotype.Service;

@Service
public class RandomDataCreatorService extends AbstractActor {

    public Company createCompany() {
        Company item = new Company();
        item.setName(faker.company().name());
        item.setIndustry(faker.company().industry());
        item.setUrl(faker.company().url());
        item.setCountry(faker.country().name());
        return item;
    }

}
