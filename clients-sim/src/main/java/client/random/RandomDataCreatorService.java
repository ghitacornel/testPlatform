package client.random;

import client.common.AbstractActor;
import client.model.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataCreatorService extends AbstractActor {

    private final RandomDataFetchService randomDataFetchService;

    public Client createClient() {
        Client item = new Client();
        item.setName(faker.name().username());
        item.setCardType(faker.business().creditCardType());
        item.setCountry(faker.country().name());
        return item;
    }

}
