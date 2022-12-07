package products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import products.repository.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.status = products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllActive();

    @Query("select p from Product p where p.status <> products.repository.entity.ProductStatus.ACTIVE")
    List<Product> findAllNotActive();

    List<Product> findByCompanyId(Integer id);

}
