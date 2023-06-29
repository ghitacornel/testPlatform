package client.actor;

import client.common.AbstractActor;
import client.model.Client;
import client.random.RandomDataCreatorService;
import client.random.RandomDataFetchService;
import client.service.ClientService;
import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayClient extends AbstractActor {

    private static final int MINIMUM = 100;
    private static final int MAXIMUM = 200;
    private final ClientService clientService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void register() {
        if (clientService.count() > MAXIMUM) {
            return;
        }
        Client client = randomDataCreatorService.createClient();
        IdResponse response = clientService.register(client);
        log.info("registered client " + response);
    }

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void unregister() {
        if (clientService.count() < MINIMUM) {
            return;
        }
        Client client = randomDataFetchService.findRandomClient();
        clientService.unregister(client);
        log.info("unregistered client" + client);
    }

}
