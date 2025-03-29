package flows.scheduler;

import flows.clients.ClientClient;
import flows.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ClientsScheduler {

    private final ClientClient clientClient;
    private final ClientService clientService;

    @Scheduled(fixedRate = 10000)
    void deleteRetiring() {
        clientClient.findRetiringIds().forEach(clientService::deleteRetiringClient);
    }

}

