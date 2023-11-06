package clients.service;

import clients.controller.model.response.ClientStatistics;
import clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final ClientRepository repository;

    public ClientStatistics getStatistics() {
        return ClientStatistics.builder()
                .countAll(repository.count())
                .build();
    }
}
