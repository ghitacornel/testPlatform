package platform.fakers;

import com.github.javafaker.Faker;
import contracts.clients.ClientRegisterRequest;

public class ClientRegisterRequestFaker {

    private static final Faker faker = Faker.instance();

    public static ClientRegisterRequest fake() {
        return ClientRegisterRequest.builder()
                .name(faker.name().username())
                .cardType(faker.business().creditCardType())
                .country(faker.country().name())
                .build();
    }

}
