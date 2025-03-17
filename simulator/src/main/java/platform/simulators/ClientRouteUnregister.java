package platform.simulators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import platform.clients.ClientClient;
import platform.clients.FlowsClient;
import platform.utils.GenerateUtils;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientRouteUnregister {

    public static final int MINIMUM = 100;

    private final Random random = new Random();

    private final ClientClient clientClient;
    private final FlowsClient flowsClient;

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    void simulate() {
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

