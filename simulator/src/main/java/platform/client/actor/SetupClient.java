package platform.client.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import platform.client.model.Client;
import platform.client.service.ClientService;
import platform.common.AbstractActor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetupClient extends AbstractActor {

    private static final int MAX_CLIENTS_NUMBER = 50;
    private final ClientService service;

    @PostConstruct
    public void setUp() {
        tearDown();
        Set<Client> items = new HashSet<>();
        while (items.size() < MAX_CLIENTS_NUMBER) items.add(createNew());
        for (Client item : items) {
            Client registered = service.register(item);
            log.info("registered " + registered);
        }
    }

    private Client createNew() {
        Client item = new Client();
        item.setName(faker.name().username());
        item.setCardType(faker.business().creditCardType());
        return item;
    }

    @PreDestroy
    public void tearDown() {
        for (Client item : service.findAll()) {
            log.info("unregistering " + item);
            service.unregister(item);
        }
    }
}
