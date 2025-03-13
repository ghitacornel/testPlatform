package companies.repository;

import companies.repository.entity.Company;
import companies.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByStatus(Status status);

    default List<Company> findAllActive() {
        return findByStatus(Status.ACTIVE);
    }

    long countByStatus(Status status);

    default long countAllActive() {
        return countByStatus(Status.ACTIVE);
    }

    @Query("select c.id from Company c where c.status = companies.repository.entity.Status.ACTIVE")
    List<Integer> findActiveIds();

    @Query("select c.id from Company c where c.status = companies.repository.entity.Status.RETIRED")
    List<Integer> findRetiredIds();

}
