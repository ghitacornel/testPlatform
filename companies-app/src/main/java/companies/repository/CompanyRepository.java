package companies.repository;

import companies.repository.entity.Company;
import companies.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByStatus(Status status);

    Status findStatusById(int id);

    default List<Company> findAllActive() {
        return findByStatus(Status.ACTIVE);
    }

    long countByStatus(Status status);

    default long countAllActive() {
        return countByStatus(Status.ACTIVE);
    }
}
