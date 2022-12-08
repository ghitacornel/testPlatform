package platform.client.actor;

import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import platform.client.model.Client;
import platform.client.service.ClientService;
import platform.common.AbstractActor;
import platform.random.RandomDataCreatorService;

import javax.annotation.PostConstruct;

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
