package orders.repository;

import orders.repository.entity.Order;
import orders.repository.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatus(OrderStatus status);

    default List<Order> findAllNew() {
        return findByStatus(OrderStatus.NEW);
    }

    @Query("select o from Order o where o.clientId = :id and o.status = orders.repository.entity.OrderStatus.NEW")
    List<Order> findAllNewForClientId(@Param("id") Integer id);

    @Query("select o from Order o where o.productId = :id and o.status = orders.repository.entity.OrderStatus.NEW")
    List<Order> findAllNewForProductId(@Param("id") Integer id);

    default List<Order> findAllCompleted() {
        return findByStatus(OrderStatus.COMPLETED);
    }

    default List<Order> findAllCancelled() {
        return findByStatus(OrderStatus.CANCELLED);
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
