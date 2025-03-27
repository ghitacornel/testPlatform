package clients.service;

import clients.mapper.ClientMapper;
import clients.repository.ClientRepository;
import clients.repository.entity.Client;
import commons.model.IdResponse;
import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;

    public IdResponse register(ClientRegisterRequest request) {
        Client entity = clientMapper.map(request);
        repository.save(entity);
        log.info("registered {}", entity);
        return new IdResponse(entity.getId());
    }

    public void retire(Integer id) {
        Client client = repository.findById(id).orElse(null);
        if (client == null) {
            log.warn("client not found {}", id);
            return;
        }
        client.retire();
        log.info("unregistered {}", id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("deleted {}", id);
    }

}