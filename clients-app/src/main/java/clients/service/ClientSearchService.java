package clients.service;

import clients.exceptions.ClientNotFoundException;
import clients.mapper.ClientMapper;
import clients.repository.ClientRepository;
import clients.repository.entity.Status;
import contracts.clients.ClientDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientSearchService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    public List<ClientDetailsResponse> findAll() {
        return repository.findByStatus(Status.ACTIVE).stream().map(mapper::map).toList();
    }

    public List<Integer> findActiveIds() {
        return repository.findIdsByStatus(Status.ACTIVE);
    }

    public List<Integer> findRetiredIds() {
        return repository.findIdsByStatus(Status.RETIRED);
    }

    public ClientDetailsResponse findById(Integer id) {
        return repository.findById(id).map(mapper::map).orElseThrow(() -> new ClientNotFoundException(id));
    }

    public long countAllActive() {
        return repository.countByStatus(Status.ACTIVE);
    }

}
