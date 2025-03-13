package products.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import products.repository.entity.Product;

import products.repository.entity.ProductStatus;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByStatus(ProductStatus status);

    @Query("select p from Product p where p.companyId = :id and p.status = products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllActiveForCompany(@Param("id") Integer id);

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

    @Modifying
    @Query("update Product p set p.status = products.repository.entity.ProductStatus.CANCELLED where p.companyId = :id")
    void cancelByCompany(@Param("id") Integer id);

}
