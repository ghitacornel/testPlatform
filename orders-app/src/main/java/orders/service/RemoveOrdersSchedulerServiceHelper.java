package orders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.mapper.OrderMapper;
import orders.repository.OrderArchiveRepository;
import orders.repository.OrderRepository;
import orders.repository.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveOrdersSchedulerServiceHelper {

    private final OrderRepository repository;
    private final OrderMapper orderMapper;
    private final OrderArchiveRepository archiveRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void delete(Order order) {
        archiveRepository.save(orderMapper.mapToArchive(order));
        repository.deleteById(order.getId());
        log.info("Deleted {}", order.getId());
    }

}
