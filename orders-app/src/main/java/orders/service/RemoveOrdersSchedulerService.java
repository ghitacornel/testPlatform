package orders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.mapper.OrderMapper;
import orders.repository.OrderArchiveRepository;
import orders.repository.OrderRepository;
import orders.repository.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveOrdersSchedulerService {

    private final OrderRepository repository;
    private final OrderMapper orderMapper;
    private final OrderArchiveRepository archiveRepository;

    @Autowired
    private RemoveOrdersSchedulerService SELF;

    public void removeCompletedOrders() {
        repository.findAllCompleted().forEach(SELF::delete);
    }

    public void removeCancelledOrders() {
        repository.findAllCancelled().forEach(SELF::delete);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void delete(Order order) {
        archiveRepository.save(orderMapper.mapToArchive(order));
        repository.deleteById(order.getId());
        log.info("Deleted {}", order.getId());
    }

}
