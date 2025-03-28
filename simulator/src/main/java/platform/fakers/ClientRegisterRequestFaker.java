package platform.fakers;

import contracts.clients.ClientRegisterRequest;
import net.datafaker.Faker;

public class ClientRegisterRequestFaker {

    private static final Faker faker = new Faker();

    public static ClientRegisterRequest fake() {
        return ClientRegisterRequest.builder()
                .name(faker.name().fullName())
                .cardType(faker.business().creditCardType())
                .country(faker.country().name())
                .build();
    }

}
