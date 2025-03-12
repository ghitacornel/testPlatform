package orders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.mapper.OrderMapper;
import orders.repository.OrderArchiveRepository;
import orders.repository.OrderRepository;
import orders.repository.entity.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveOrdersSchedulerServiceHelper {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderArchiveRepository archiveRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Async
    public void delete(Order order) {
        archiveRepository.save(mapper.mapToArchive(order));
        repository.deleteById(order.getId());
        log.info("Deleted {}", order.getId());
    }

}
