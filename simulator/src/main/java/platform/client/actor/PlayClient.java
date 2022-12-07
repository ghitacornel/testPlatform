package platform.client.actor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.client.model.Client;
import platform.client.service.ClientService;
import platform.common.AbstractActor;
import platform.random.RandomDataCreatorService;
import platform.random.RandomDataFetchService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayClient extends AbstractActor {

    private static final int MINIMUM_CLIENTS_COUNT = 50;
    private static final int MAXIMUM_CLIENTS_COUNT = 200;
    private final ClientService clientService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void register() {
        if (clientService.count() > MAXIMUM_CLIENTS_COUNT) {
            return;
        }
        Client client = randomDataCreatorService.createClient();
        client = clientService.register(client);
        log.info("registered client " + client);
    }

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void unregister() {
        if (clientService.count() < MINIMUM_CLIENTS_COUNT) {
            return;
        }
        Client client = randomDataFetchService.findRandomClient();
        clientService.unregister(client);
        log.info("unregistered client" + client);
    }

}
