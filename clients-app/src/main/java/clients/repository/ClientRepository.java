package clients.repository;

import clients.repository.entity.Client;
import clients.repository.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByStatus(Status status);

    long countByStatus(Status status);

    @Query("select c.id from Client c where c.status = :status")
    List<Integer> findIdsByStatus(@Param("status") Status status);

    @Modifying
    @Query("delete from Client c where c.status = :status")
    void deleteByStatus(Status status);

}
