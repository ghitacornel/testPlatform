package products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import products.repository.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllActive();

    @Query("select p from Product p where p.status <> products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllNotActive();

    List<Product> findByCompanyId(Integer id);

    @Query("select count(p.id) from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE")
    long countAllActive();

    @Modifying(flushAutomatically = true)
    @Query("update Product p set p.status = products.repository.entity.ProductStatus.CANCELLED where p.companyId = :companyId")
    void cancelByCompanyId(@Param("companyId") Integer companyId);

}
