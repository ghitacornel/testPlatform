package clients.service;

import clients.controller.model.request.ClientRegisterRequest;
import clients.controller.model.response.ClientDetailsResponse;
import clients.controller.model.response.ClientStatistics;
import clients.mapper.ClientMapper;
import clients.repository.ClientRepository;
import clients.repository.entity.Client;
import commons.exceptions.ResourceNotFound;
import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;

    public List<ClientDetailsResponse> findAll() {
        return repository.findAll().stream()
                .map(clientMapper::map)
                .collect(Collectors.toList());
    }

    public ClientDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(clientMapper::map)
                .orElseThrow(() -> new ResourceNotFound("Client with id " + id + " not found"));
    }

    public IdResponse register(ClientRegisterRequest request) {
        Client entity = clientMapper.map(request);
        repository.save(entity);
        log.info("registered " + entity);
        return new IdResponse(entity.getId());
    }

    public long count() {
        return repository.count();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
        log.info("unregistered " + id);
    }

    public ClientStatistics getStatistics() {
        return ClientStatistics.builder()
                .countAll(repository.count())
                .build();
    }
}
