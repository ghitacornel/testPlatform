package products.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import products.repository.entity.Product;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllActive();

    @Query("select count(p.id) from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE")
    long countAllActive();

    @Query("select count(p.id) from Product p where p.status = products.repository.entity.ProductStatus.CANCELLED")
    long countAllCancelled();

    @Query("select count(p.id) from Product p where p.status = products.repository.entity.ProductStatus.CONSUMED")
    long countAllConsumed();

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "100")})
    @Query("select p from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE and p.id = :id")
    Optional<Product> findByIdAndLock(@Param("id") Integer id);

    @Modifying
    void deleteProductsByCompanyId(Integer id);

}
