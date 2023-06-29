package clients.service;

import clients.controller.model.request.ClientRegisterRequest;
import clients.controller.model.response.ClientDetailsResponse;
import clients.repository.ClientRepository;
import clients.repository.entity.Client;
import clients.service.mapper.ClientMapper;
import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + id + " not found"));
    }

    public IdResponse register(ClientRegisterRequest request) {
        Client client = clientMapper.map(request);
        repository.save(client);
        return new IdResponse(client.getId());
    }

    public long count() {
        return repository.count();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
