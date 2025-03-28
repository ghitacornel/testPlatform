package companies.repository;

import companies.repository.entity.Company;
import companies.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByStatus(Status status);

    long countByStatus(Status status);

    @Query("select c.id from Company c where c.status = :status")
    List<Integer> findIdsByStatus(@Param("status") Status status);

    @Modifying
    @Query("delete from Company c where c.status = :status")
    void deleteByStatus(@Param("status") Status status);

}
