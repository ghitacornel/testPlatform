package products.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import products.repository.entity.Product;

import products.repository.entity.Status;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByStatus(Status status);

    @Query("select p from Product p where p.companyId = :id and p.status = products.repository.entity.Status.ACTIVE")
    List<Product> findAllActiveForCompany(@Param("id") Integer id);

    default List<Product> findAllActive() {
        return findByStatus(Status.ACTIVE);
    }

    long countByStatus(Status status);

    default long countAllActive() {
        return countByStatus(Status.ACTIVE);
    }

    @Query("select p.id from Product p where p.status = products.repository.entity.Status.CONSUMED")
    List<Integer> findConsumedIds();

    @Query("select p.id from Product p where p.status = products.repository.entity.Status.CANCELLED")
    List<Integer> findCancelledIds();

    @Query("select p.id from Product p where p.status = products.repository.entity.Status.ACTIVE")
    List<Integer> findActiveIds();

    List<Product> findByCompanyId(Integer id);

    boolean existsByCompanyId(Integer id);

}
