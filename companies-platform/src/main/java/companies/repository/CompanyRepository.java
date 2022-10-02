package companies.repository;

import companies.repository.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Modifying
    @Query("delete from Company p where p.status = companies.repository.entity.CompanyStatus.DELETED")
    void deleteAllMarked();

    @Query("select p from Company p where p.status = companies.repository.entity.CompanyStatus.ACTIVE")
    List<Company> findAllActive();
}
