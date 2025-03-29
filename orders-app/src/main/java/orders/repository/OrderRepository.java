package orders.repository;

import contracts.orders.Status;
import orders.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatus(Status status);

    @Modifying
    @Query("delete from Order o where o.status = :status")
    void deleteByStatus(@Param("status") Status status);

    @Query("select o.id from Order o where o.status = :status")
    List<Integer> findIdsByStatus(@Param("status") Status status);

    @Query("select o.id from Order o where o.clientId = :id and o.status = contracts.orders.Status.NEW")
    List<Integer> findNewIdsForClientId(@Param("id") Integer id);

    @Query("select o.id from Order o where o.productId = :id and o.status = contracts.orders.Status.NEW")
    List<Integer> findNewIdsForProductId(@Param("id") Integer id);

    boolean existsByProductId(Integer id);

    boolean existsByClientId(Integer id);

    long countByStatus(Status status);

    @Query("select o.id from Order o where o.productId = :id and o.status = contracts.orders.Status.NEW")
    List<Integer> findNewIdsByProductId(@Param("id") Integer id);

}
