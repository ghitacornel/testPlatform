package platform.client.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import platform.client.model.Client;
import platform.client.service.ClientService;
import platform.common.AbstractActor;
import platform.random.RandomDataCreatorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupClient extends AbstractActor {

    private static final int MAX_CLIENTS_NUMBER = 50;
    private final ClientService clientService;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {
        tearDown();
        Set<Client> items = new HashSet<>();
        while (items.size() < MAX_CLIENTS_NUMBER) items.add(randomDataCreatorService.createClient());
        for (Client item : items) {
            Client registered = clientService.register(item);
            log.info("registered client " + registered);
        }
    }

    @PreDestroy
    public void tearDown() {
        for (Client item : clientService.findAll()) {
            clientService.unregister(item);
            log.info("unregistered client" + item);
        }
    }
}
