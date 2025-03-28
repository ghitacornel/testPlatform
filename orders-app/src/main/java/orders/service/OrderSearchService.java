package orders.service;

import contracts.orders.OrderDetailsResponse;
import contracts.orders.Status;
import lombok.RequiredArgsConstructor;
import orders.exceptions.OrderNotFoundException;
import orders.mapper.OrderMapper;
import orders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderSearchService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public List<OrderDetailsResponse> findAllNew() {
        return repository.findByStatus(Status.NEW).stream().map(mapper::map).toList();
    }

    public List<Integer> findCompletedIds() {
        return repository.findIdsByStatus(Status.COMPLETED);
    }

    public List<Integer> findSentIds() {
        return repository.findIdsByStatus(Status.INVOICING);
    }

    public List<Integer> findInvoicedIds() {
        return repository.findIdsByStatus(Status.INVOICED);
    }

    public List<Integer> findNewIds() {
        return repository.findIdsByStatus(Status.NEW);
    }

    public List<Integer> findRejectedIds() {
        return repository.findIdsByStatus(Status.REJECTED);
    }

    public List<Integer> findAllNewForClientId(Integer id) {
        return repository.findNewIdsForClientId(id);
    }

    public List<Integer> findAllNewForProductId(Integer id) {
        return repository.findNewIdsForProductId(id);
    }

    public OrderDetailsResponse findById(Integer id) {
        return repository.findById(id).map(mapper::map).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public boolean existsByProductId(Integer id) {
        return repository.existsByProductId(id);
    }

    public boolean existsByClientId(Integer id) {
        return repository.existsByClientId(id);
    }

    public long countAllNew() {
        return repository.countByStatus(Status.NEW);
    }

}
