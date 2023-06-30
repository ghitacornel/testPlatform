package client.actor;

import client.common.AbstractActor;
import client.model.Client;
import client.random.RandomDataCreatorService;
import client.service.ClientService;
import commons.model.IdResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupClient extends AbstractActor {

    private static final int MAX_CLIENTS_NUMBER = 50;

    private final ClientService clientService;
    private final RandomDataCreatorService randomDataCreatorService;

    @PostConstruct
    public void setUp() {
        while (clientService.count() < MAX_CLIENTS_NUMBER) {
            try {
                Client client = randomDataCreatorService.createClient();
                IdResponse response = clientService.register(client);
                log.info("registered client " + response);
            } catch (Exception e) {
                log.error("fail creating a client, trying again", e);
            }
        }
    }

}
