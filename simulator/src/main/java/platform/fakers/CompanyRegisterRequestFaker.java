package platform.fakers;

import contracts.companies.CompanyRegisterRequest;
import net.datafaker.Faker;

public class CompanyRegisterRequestFaker {

    private static final Faker faker = new Faker();

    public static CompanyRegisterRequest fake() {
        return CompanyRegisterRequest.builder()
                .name(faker.company().name())
                .industry(faker.company().industry())
                .url(faker.company().url())
                .country(faker.country().name())
                .build();
    }
}
