package clients.repository;

import clients.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Modifying
    @Query("delete from Client p where p.status = clients.repository.entity.ClientStatus.DELETED")
    void deleteAllMarked();

}
