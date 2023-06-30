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

    @Query("select p from Product p where p.status <> products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllNotActive();

    @Query("select count(p.id) from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE")
    long countAllActive();

    @Modifying(flushAutomatically = true)
    @Query("update Product p set p.status = products.repository.entity.ProductStatus.CANCELLED where p.companyId = :companyId")
    void cancelByCompanyId(@Param("companyId") Integer companyId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "100")})
    @Query("select p from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE and p.id = :id")
    Optional<Product> findByIdAndLock(@Param("id") Integer id);
}
