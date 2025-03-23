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

    default List<Order> findAllNew() {
        return findByStatus(Status.NEW);
    }

    @Modifying
    @Query("delete from Order o where o.status = contracts.orders.Status.CANCELLED")
    void deleteAllByStatusCancelled();

    @Modifying
    @Query("delete from Order o where o.status = contracts.orders.Status.INVOICED")
    void deleteAllByStatusInvoiced();

    @Query("select o.id from Order o where o.status = contracts.orders.Status.COMPLETED")
    List<Integer> findCompletedIds();

    @Query("select o.id from Order o where o.status = contracts.orders.Status.INVOICED")
    List<Integer> findInvoicedIds();

    @Query("select o.id from Order o where o.status = contracts.orders.Status.NEW")
    List<Integer> findNewIds();

    @Query("select o.id from Order o where o.status = contracts.orders.Status.REJECTED")
    List<Integer> findRejectedIds();

    @Query("select o from Order o where o.clientId = :id and o.status = contracts.orders.Status.NEW")
    List<Order> findAllNewForClientId(@Param("id") Integer id);

    @Query("select o from Order o where o.productId = :id and o.status = contracts.orders.Status.NEW")
    List<Order> findAllNewForProductId(@Param("id") Integer id);

    boolean existsByProductId(Integer id);

    boolean existsByClientId(Integer id);

    long countByStatus(Status status);

    default long countAllNew() {
        return countByStatus(Status.NEW);
    }

    @Query("select o.id from Order o where o.productId = :id and o.status = contracts.orders.Status.NEW")
    List<Integer> findActiveIdsByProductId(@Param("id") Integer id);

}
