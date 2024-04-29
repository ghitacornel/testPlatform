package products.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import products.repository.entity.Product;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import products.repository.entity.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByStatus(ProductStatus status);

    default List<Product> findAllActive() {
        return findByStatus(ProductStatus.ACTIVE);
    }

    default List<Product> findAllConsumed() {
        return findByStatus(ProductStatus.CONSUMED);
    }

    default List<Product> findAllCancelled() {
        return findByStatus(ProductStatus.CANCELLED);
    }

    long countByStatus(ProductStatus status);

    default long countAllActive() {
        return countByStatus(ProductStatus.ACTIVE);
    }

    default long countAllCancelled() {
        return countByStatus(ProductStatus.CANCELLED);
    }

    default long countAllConsumed() {
        return countByStatus(ProductStatus.CONSUMED);
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "100")})
    @Query("select p from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE and p.id = :id")
    Optional<Product> findByIdAndLock(@Param("id") Integer id);

    @Modifying
    @Query("update Product p set p.status = products.repository.entity.ProductStatus.CANCELLED where p.companyId = :id")
    void cancelByCompany(@Param("id") Integer id);

}
