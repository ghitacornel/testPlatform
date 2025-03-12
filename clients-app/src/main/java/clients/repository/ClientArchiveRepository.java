package clients.repository;

import clients.repository.entity.ClientArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientArchiveRepository extends JpaRepository<ClientArchive, Integer> {
}
