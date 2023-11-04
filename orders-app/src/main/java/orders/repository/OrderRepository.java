package orders.repository;

import orders.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select p from Order p where p.status = orders.repository.entity.OrderStatus.NEW")
    List<Order> findAllNew();

    @Modifying
    @Query("delete from Order o where o.status = orders.repository.entity.OrderStatus.COMPLETED")
    void deleteCompleted();

    boolean existsByProductId(Integer id);

    @Query("select count(p.id) from Order p where p.status = orders.repository.entity.OrderStatus.NEW")
    long countAllNew();

    @Query("select count(p.id) from Order p where p.status = orders.repository.entity.OrderStatus.CANCELLED")
    long countAllCancelled();

    @Query("select count(p.id) from Order p where p.status = orders.repository.entity.OrderStatus.COMPLETED")
    long countAllCompleted();
}
