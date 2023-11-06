package orders.service;

import lombok.RequiredArgsConstructor;
import orders.controller.model.response.Statistics;
import orders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final OrderRepository repository;

    public Statistics getStatistics() {
        return Statistics.builder()
                .countAll(repository.count())
                .countNew(repository.countAllNew())
                .countCancelled(repository.countAllCancelled())
                .countCompleted(repository.countAllCompleted())
                .build();
    }

}
