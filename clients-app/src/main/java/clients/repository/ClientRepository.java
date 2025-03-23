package clients.repository;

import clients.repository.entity.Client;
import clients.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByStatus(Status status);

    long countByStatus(Status status);

    @Query("select c.id from Client c where c.status = clients.repository.entity.Status.ACTIVE")
    List<Integer> findActiveIds();

    @Query("select c.id from Client c where c.status = clients.repository.entity.Status.RETIRED")
    List<Integer> findRetiredIds();

}
