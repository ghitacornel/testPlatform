package clients.service;

import clients.controller.model.ClientDto;
import clients.controller.model.ClientRegisterRequest;
import clients.repository.ClientRepository;
import clients.repository.entity.Client;
import clients.service.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;

    public List<ClientDto> findAll() {
        return repository.findAll().stream()
                .map(clientMapper::map)
                .collect(Collectors.toList());
    }

    public ClientDto findByName(String name) {
        return repository.findByName(name)
                .map(clientMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Client with user name " + name + " not found"));
    }

    public ClientDto register(ClientRegisterRequest request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new ValidationException("username taken");
        }
        Client client = new Client();
        client.setName(request.getName());
        client.setCardType(request.getCardType());
        repository.save(client);
        return clientMapper.map(client);
    }

    public void unregister(String name) {
        Client client = repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Client with user name " + name + " not found"));
        repository.delete(client);
    }

}
