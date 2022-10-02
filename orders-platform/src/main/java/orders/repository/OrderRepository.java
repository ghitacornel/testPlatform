package orders.repository;

import orders.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Query("delete from Order o where o.status = orders.repository.entity.OrderStatus.COMPLETED")
    void deleteCompleted();

}
