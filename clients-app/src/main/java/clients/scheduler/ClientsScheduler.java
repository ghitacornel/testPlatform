package clients.scheduler;

import clients.repository.ClientRepository;
import clients.repository.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ClientsScheduler {

    private final ClientRepository repository;

    @Scheduled(fixedRate = 10000)
    void deleteRetired() {
        repository.deleteByStatus(Status.RETIRED);
    }

}

