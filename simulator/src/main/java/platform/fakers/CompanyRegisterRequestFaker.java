package platform.fakers;

import com.github.javafaker.Faker;
import contracts.companies.CompanyRegisterRequest;

public class CompanyRegisterRequestFaker {

    private static final Faker faker = Faker.instance();

    public static CompanyRegisterRequest fake() {
        return CompanyRegisterRequest.builder()
                .name(faker.company().name())
                .industry(faker.company().industry())
                .url(faker.company().url())
                .country(faker.country().name())
                .build();
    }
}
