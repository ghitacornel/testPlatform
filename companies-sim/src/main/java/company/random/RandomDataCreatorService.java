package company.random;

import com.github.javafaker.Faker;
import company.model.Company;
import org.springframework.stereotype.Service;

@Service
public class RandomDataCreatorService {

    private final Faker faker = Faker.instance();

    public Company createCompany() {
        Company item = new Company();
        item.setName(faker.company().name());
        item.setIndustry(faker.company().industry());
        item.setUrl(faker.company().url());
        item.setCountry(faker.country().name());
        return item;
    }

}
