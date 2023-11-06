package products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.controller.model.response.Statistics;
import products.repository.ProductRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final ProductRepository repository;

    public Statistics getStatistics() {
        return Statistics.builder()
                .countAll(repository.count())
                .countActive(repository.countAllActive())
                .countCancelled(repository.countAllCancelled())
                .countConsumed(repository.countAllConsumed())
                .build();
    }
}
