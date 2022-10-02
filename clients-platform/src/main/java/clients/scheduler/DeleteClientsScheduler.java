package clients.scheduler;

import clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteClientsScheduler {

    private final ClientRepository repository;

    @Scheduled(fixedRate = 10000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteAllMarked() {
        repository.deleteAllMarked();
    }

}
