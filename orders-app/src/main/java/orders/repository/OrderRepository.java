package orders.repository;

import orders.repository.entity.Order;
import orders.repository.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatus(OrderStatus status);

    default List<Order> findAllNew() {
        return findByStatus(OrderStatus.NEW);
    }

    boolean existsByProductId(Integer id);

    long countByStatus(OrderStatus status);

    default long countAllNew() {
        return countByStatus(OrderStatus.NEW);
    }

    default long countAllCancelled() {
        return countByStatus(OrderStatus.CANCELLED);
    }

    default long countAllCompleted() {
        return countByStatus(OrderStatus.COMPLETED);
    }

}
