package flows.scheduler;

import flows.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ClientsScheduler {

    private final ClientService clientService;

    @Scheduled(fixedRate = 10000)
    void deleteRetired() {
        clientService.deleteRetired();
    }

}

