package clients.service;

import clients.exceptions.ClientNotFoundException;
import clients.mapper.ClientMapper;
import clients.repository.ClientRepository;
import clients.repository.entity.Client;
import clients.repository.entity.Status;
import commons.model.IdResponse;
import contracts.clients.ClientDetailsResponse;
import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;

    public List<ClientDetailsResponse> findAll() {
        return repository.findByStatus(Status.ACTIVE).stream()
                .map(clientMapper::map)
                .toList();
    }

    public List<Integer> findActiveIds() {
        return repository.findActiveIds();
    }

    public List<Integer> findRetiredIds() {
        return repository.findRetiredIds();
    }

    public ClientDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(clientMapper::map)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    public IdResponse register(ClientRegisterRequest request) {
        Client entity = clientMapper.map(request);
        repository.save(entity);
        log.info("registered {}", entity);
        return new IdResponse(entity.getId());
    }

    public long countAllActive() {
        return repository.countByStatus(Status.ACTIVE);
    }

    public void retire(Integer id) {
        repository.findById(id).ifPresent(Client::retire);
        log.info("unregistered {}", id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("deleted {}", id);
    }
}
