package products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import products.repository.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Modifying
    @Query("delete from Product p where p.status = products.repository.entity.ProductStatus.CANCELLED")
    void deleteCancelled();

}
