package products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import products.repository.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllActive();

    @Modifying
    @Query("delete from Product p where p.status = products.repository.entity.ProductStatus.CANCELLED")
    void deleteCancelled();

}
