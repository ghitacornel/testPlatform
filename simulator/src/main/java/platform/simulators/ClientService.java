package platform.simulators;

import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.fakers.ClientRegisterRequestFaker;
import platform.utils.GenerateUtils;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientService {

    public static final int MINIMUM = 100;
    private static final int MAXIMUM = 200;

    private final ClientClient clientClient;
    private final FlowsClient flowsClient;

    private final Random random = new Random();

    @Async
    public void register() {

        long count = clientClient.count();
        if (count > MAXIMUM) {
            return;
        }
        ClientRegisterRequest fake = ClientRegisterRequestFaker.fake();

        try {
            clientClient.register(fake);
            log.info("register client {}", fake);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void unregister() {
        long count = clientClient.count();
        if (count <= MINIMUM) {
            return;
        }

        List<Integer> activeIds = clientClient.findActiveIds();
        Integer id = GenerateUtils.random(activeIds, random);
        if (id == null) {
            log.warn("No active clients available for unregistering");
            return;
        }

        try {
            flowsClient.deleteClient(id);
            log.info("unregister client {}", id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}

