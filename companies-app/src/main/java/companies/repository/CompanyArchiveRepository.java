package companies.repository;

import companies.repository.entity.CompanyArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyArchiveRepository extends JpaRepository<CompanyArchive, Integer> {
}
