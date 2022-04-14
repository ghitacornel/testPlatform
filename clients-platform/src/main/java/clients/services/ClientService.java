package clients.services;

import clients.services.mappers.ClientMapper;
import clients.controllers.models.ClientDto;
import clients.repositories.entities.Client;
import clients.controllers.models.ClientRegisterRequest;
import clients.repositories.ClientRepository;
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
        repository.findByName(name).ifPresent(repository::delete);
    }

}
