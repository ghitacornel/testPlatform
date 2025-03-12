package clients.service;

import clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveClientsSchedulerService {

    private final ClientRepository repository;
    private final RemoveClientsSchedulerServiceHelper helper;

    public void removeRetiredClients() {
        repository.findAllActive().forEach(helper::delete);
    }

}
