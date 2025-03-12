package clients.repository;

import clients.repository.entity.Client;
import clients.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByStatus(Status status);

    Status findStatusById(int id);

    default List<Client> findAllActive() {
        return findByStatus(Status.ACTIVE);
    }

    long countByStatus(Status status);

    default long countAllActive() {
        return countByStatus(Status.ACTIVE);
    }
}
