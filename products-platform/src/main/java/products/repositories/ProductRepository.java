package products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import products.repositories.entities.Product;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select p from Product p where p.id = :id")
    Optional<Product> lockForProcess(@Param("id") Integer id);

}
