package clients.service;

import clients.mapper.ClientMapper;
import clients.repository.ClientArchiveRepository;
import clients.repository.ClientRepository;
import clients.repository.entity.Client;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveClientsSchedulerServiceHelper {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final ClientArchiveRepository archiveRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Async
    public void delete(Client client) {
        archiveRepository.save(mapper.mapToArchive(client));
        repository.deleteById(client.getId());
        log.info("Deleted {}", client.getId());
    }

}
