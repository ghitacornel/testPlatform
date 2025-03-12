package companies.repository;

import companies.repository.entity.Company;
import companies.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByStatus(Status status);

    @Query("select c.status from Company c where c.id = :id")
    Status findStatusById(@Param("id") int id);

    default List<Company> findAllActive() {
        return findByStatus(Status.ACTIVE);
    }

    default List<Company> findAllRetired() {
        return findByStatus(Status.RETIRED);
    }

    long countByStatus(Status status);

    default long countAllActive() {
        return countByStatus(Status.ACTIVE);
    }
}
