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

    private final ClientService clientService;
    private final RandomDataFetchService randomDataFetchService;
    private final RandomDataCreatorService randomDataCreatorService;

    @Scheduled(fixedRate = 1000, initialDelay = 2000)
    public void register() {
        Client client = randomDataCreatorService.createClient();
        clientService.register(client);
        log.info("registered client " + client);
    }

    @Scheduled(fixedRate = 1000, initialDelay = 2000)
    public void unregister() {
        Client client = randomDataFetchService.findRandomClient();
        clientService.unregister(client);
        log.info("unregistered client" + client);
    }

}
