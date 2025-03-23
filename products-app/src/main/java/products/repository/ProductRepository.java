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

    long countByStatus(Status status);

    @Query("select p.id from Product p where p.status = :status")
    List<Integer> findIdsByStatus(@Param("status") Status status);

    List<Product> findByCompanyId(Integer id);

    boolean existsByCompanyId(Integer id);

}
